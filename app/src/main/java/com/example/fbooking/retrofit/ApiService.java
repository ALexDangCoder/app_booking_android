package com.example.fbooking.retrofit;

import com.example.fbooking.room.Data;
import com.example.fbooking.room.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/get-all-list-room")
    Call<Data> getListRoom();
}
