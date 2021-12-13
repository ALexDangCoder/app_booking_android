package com.example.fbooking.accept;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultAccept {
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Accept> acceptList = null;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Accept> getAcceptList() {
        return acceptList;
    }

    public void setData(List<Accept> acceptList) {
        this.acceptList = acceptList;
    }

}
