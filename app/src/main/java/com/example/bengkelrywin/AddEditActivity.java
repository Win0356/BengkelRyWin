package com.example.bengkelrywin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bengkelrywin.SharedPreference.UserPreferences;
import com.example.bengkelrywin.api.ApiClient;
import com.example.bengkelrywin.api.ApiInterface;
import com.example.bengkelrywin.models.Service;
import com.example.bengkelrywin.models.ServiceResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditActivity extends AppCompatActivity {

    private static final String[] JENIS_SERVICE_LIST = new String[]{"Pompa Ban", "Ganti Oli", "Ganti Suku Cadang", "Reparasi"};

    private UserPreferences userPreferences;
    private ApiInterface apiService;
    private EditText etNama, etAlamat;
    private AutoCompleteTextView edJenis;
    private LinearLayout layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        setTitle("Service");

        apiService = ApiClient.getClient().create(ApiInterface.class);

        userPreferences = new UserPreferences(this);

        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        edJenis = findViewById(R.id.ed_jenis);
        layoutLoading = findViewById(R.id.layout_loading);

        ArrayAdapter<String> adapterJenis =
                new ArrayAdapter<>(this, R.layout.item_list, JENIS_SERVICE_LIST);
        edJenis.setAdapter(adapterJenis);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);
        long id = getIntent().getLongExtra("id", -1);

        if (id == -1) {
            tvTitle.setText("Tambah Service");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createService();
                }
            });
        }
    }


    private void createService() {
        setLoading(true);

        Service service = new Service(
                etNama.getText().toString(),
                edJenis.getText().toString(),
                etAlamat.getText().toString());

        Call<ServiceResponse> call = apiService.createService("Bearer " + userPreferences.getAccessToken(), service);

        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call,
                                   Response<ServiceResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEditActivity.this,
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new
                                JSONObject(response.errorBody().string());
                        Toast.makeText(AddEditActivity.this,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AddEditActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                setLoading(false);
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Toast.makeText(AddEditActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        });
    }


    // Fungsi untuk menampilkan layout loading
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }
}