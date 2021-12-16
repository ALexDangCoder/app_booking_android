package com.example.fbooking.retrofit;

import com.example.fbooking.accept.Accept;
import com.example.fbooking.accept.DeleteIdAccept;
import com.example.fbooking.accept.ResultAccept;
import com.example.fbooking.booking.Booking;
import com.example.fbooking.history.DeleteAllHistory;
import com.example.fbooking.history.History;
import com.example.fbooking.history.DeleteIdHistory;
import com.example.fbooking.history.ResultHistory;
import com.example.fbooking.room.FavoriteResult;
import com.example.fbooking.room.Result;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.Utilities;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    //Danh sach phong
    @GET("api/get-all-list-room")
    Call<Result> getListRoom();

    //Cho dat phong
    @POST("api/wait-to-accept-room")
    Call<Booking> createOrder(@Body Booking booking);

    //Lich su dat phong
    @GET("api/get-history-by-user")
    Call<ResultHistory> getListHistory(@Query("email") String email);

    //Xoa tung muc lich su
    @POST("api/delete-history-by-user")
    Call<History> deleteHistory(@Body DeleteIdHistory body);

    //Xoa het lich su
    @POST("api/delete-all-history-by-user")
    Call<ResultHistory> deleteAllHistory(@Body DeleteAllHistory deleteAllHistory);

    //Danh sach pho bien
    @GET("api/get-top-favorite")
    Call<Result> getListFavorite();

    //Filter danh sach
    @GET("api/get-filter-Room")
    Call<Result> getFilter(@QueryMap Map<String, Boolean> option);

    //Danh sach phong cho xac nhan
    @GET("api/get-list-wait-to-accept-room")
    Call<ResultAccept> getListAccept(@Query("email") String email);

    //Xoa cho xac nhan dat phong
    @POST("api/cancel-wait-to-accept")
    Call<Accept> deleteAccept(@Body DeleteIdAccept body);

    //Yeu thich
    @POST("api/update-favorite")
    Call<FavoriteResult> setFavoriteRoom(@Body DeleteIdAccept body);

    //Danh sach phong minh da yeu thich
    @GET("api/get-list-favorite-by-user")
    Call<Result> getListFavoriteByUser(@Query("email") String email);
}
