package com.example.androidapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("register")
    Call<ResponseBody> createUser (
            @Body User user
    );

    @POST("login")
    Call<ResponseBody> checkUser (
            @Body User user
    );
}
