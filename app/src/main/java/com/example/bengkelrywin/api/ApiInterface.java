package com.example.bengkelrywin.api;

import com.example.bengkelrywin.models.Login;
import com.example.bengkelrywin.models.Service;
import com.example.bengkelrywin.models.ServiceResponse;
import com.example.bengkelrywin.models.User;
import com.example.bengkelrywin.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Accept: application/json"})
    @POST("api/register")
    Call<UserResponse> register(@Body User user);

    @Headers({"Accept: application/json"})
    @POST("api/login")
    Call<UserResponse> login(@Body Login login);

    @Headers({"Accept: application/json"})
    @PUT("api/user")
    Call<UserResponse> editUser(@Header("Authorization") String token,
                                @Body User user);

    @Headers({"Accept: application/json"})
    @GET("api/logout")
    Call<UserResponse> logout(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("api/service")
    Call<ServiceResponse> getAllService(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("api/service")
    Call<ServiceResponse> createService(@Header("Authorization") String token,
                                        @Body Service service);
}

