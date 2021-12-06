package com.example.fbooking.retrofit;

import com.example.fbooking.booking.Booking;
import com.example.fbooking.room.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api/get-all-list-room")
    Call<Result> getListRoom();

    @POST("api/wait-to-accept-room")
    Call<Booking> createOrder(@Body Booking booking);
}
