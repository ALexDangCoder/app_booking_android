package com.example.fbooking.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServiceUpdate {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiServiceUpdate apiService = new Retrofit.Builder()
            .baseUrl("https://hotelfpoly.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServiceUpdate.class);

    @FormUrlEncoded
    @POST("update-acount")
    Call<TestLoginService> covertApi(
            @Field("name") String name,
            @Field("birthday") String birthday,
            @Field("phoneNumber") String phoneNumber,
            @Field("cccd") String cccd,
            @Field("gmail") String gmail);

}
