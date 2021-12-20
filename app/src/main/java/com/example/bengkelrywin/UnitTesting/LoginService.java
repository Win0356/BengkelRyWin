package com.example.bengkelrywin.UnitTesting;

import com.example.bengkelrywin.api.ApiClient;
import com.example.bengkelrywin.api.ApiInterface;
import com.example.bengkelrywin.models.Login;
import com.example.bengkelrywin.models.User;
import com.example.bengkelrywin.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {
    public void login(final LoginView view, Login login, final LoginCallback callback)
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> profilDAOCall =
                apiService.login(login);
        profilDAOCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call,
                                   Response<UserResponse> response) {

                if(response.body().getMessage().equalsIgnoreCase("berhasil login"
                )){
                    callback.onSuccess(true,
                            response.body().getUser());
                }
                else{
                    callback.onError();
                    view.showLoginError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable
                    t) {
                view.showErrorResponse(t.getMessage());
                callback.onError();
            }
        });
    }

    public Boolean getValid(final LoginView view, Login login) {
        final Boolean[] bool = new Boolean[1];
        login(view, login, new LoginCallback() {
            @Override
            public void onSuccess(boolean value, User user) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
