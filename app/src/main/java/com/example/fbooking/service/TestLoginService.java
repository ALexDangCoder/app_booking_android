package com.example.fbooking.service;

public class TestLoginService {
    boolean success;
    String gmail;
    String password;
    String name;

    public TestLoginService(boolean success, String gmail, String password, String name) {
        this.success = success;
        this.gmail = gmail;
        this.password = password;
        this.name = name;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
