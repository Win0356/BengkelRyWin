package com.example.bengkelrywin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bengkelrywin.SharedPreference.UserPreferences;
import com.example.bengkelrywin.adapters.ServiceAdapter;
import com.example.bengkelrywin.api.ApiClient;
import com.example.bengkelrywin.api.ApiInterface;
import com.example.bengkelrywin.models.ServiceResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private UserPreferences userPreferences;
    private SwipeRefreshLayout srService;
    private ServiceAdapter adapter;
    private ApiInterface apiService;
    private SearchView svService;
    private LinearLayout layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        userPreferences = new UserPreferences(this);

        layoutLoading = findViewById(R.id.layout_loading);
        srService = findViewById(R.id.sr_service);
        svService = findViewById(R.id.sv_service);

        srService.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllService();
            }
        });

        svService.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(i, LAUNCH_ADD_ACTIVITY);
            }
        });

        ImageButton btnLokasi = findViewById(R.id.btnLokasi);
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                finish();
            }
        });

        ImageButton btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                finish();
            }
        });

        RecyclerView rvService = findViewById(R.id.rv_service);
        adapter = new ServiceAdapter(new ArrayList<>(), this);
        rvService.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false));
        rvService.setAdapter(adapter);

        getAllService();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllService();
    }

    private void getAllService() {
        Call<ServiceResponse> call = apiService.getAllService("Bearer " + userPreferences.getAccessToken());

        srService.setRefreshing(true);

        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setServiceList(response.body().getServiceList());
                    adapter.getFilter().filter(svService.getQuery());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                srService.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error",
                        Toast.LENGTH_SHORT).show();
                srService.setRefreshing(false);
            }
        });
    }

//    public void deleteService(long id) {
//        Call<ServiceResponse> call = apiService.deleteService("Bearer " + userPreferences.getAccessToken(), id);
//
//        setLoading(true);
//
//        call.enqueue(new Callback<ServiceResponse>() {
//            @Override
//            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(MainActivity.this, response.body().getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                    getAllService();
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(MainActivity.this, jObjError.getString("message"),
//                                Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                setLoading(false);
//            }
//
//            @Override
//            public void onFailure(Call<ServiceResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Network error",
//                        Toast.LENGTH_SHORT).show();
//                setLoading(false);
//            }
//        });
//    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.GONE);
        }
    }
}