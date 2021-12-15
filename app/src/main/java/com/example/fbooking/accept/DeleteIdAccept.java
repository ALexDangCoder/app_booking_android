package com.example.fbooking.accept;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteIdAccept {
    @SerializedName("Roomid")
    @Expose
    private String id;

    @SerializedName("idRoom")
    @Expose
    private String idRoom;

    @SerializedName("userEmail")
    @Expose
    private String userEmail;

    public DeleteIdAccept() {

    }

    public DeleteIdAccept(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }
}
