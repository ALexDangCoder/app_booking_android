package com.example.fbooking.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {
    //https://run.mocky.io/v3/58db7c12-6e40-446b-a8b9-028a5f563261
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @FormUrlEncoded
    @POST("addAccount")
    Call<TestLoginService> covertApi(@Field("gmail") String gmail,
                                     @Field("password") String password, @Field("name") String name);
}
