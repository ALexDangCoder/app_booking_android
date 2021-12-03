package com.example.fbooking.retrofit;

import com.example.fbooking.room.Result;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/get-all-list-room")
    Call<Result> getListRoom();
}
