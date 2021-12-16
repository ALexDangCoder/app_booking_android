package com.example.fbooking.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteIdHistory {
    @SerializedName("id")
    @Expose
    private String id;

    public DeleteIdHistory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
