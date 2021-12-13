package com.example.fbooking.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteAllHistory {
    @SerializedName("email")
    @Expose
    private String email;

    public DeleteAllHistory(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
