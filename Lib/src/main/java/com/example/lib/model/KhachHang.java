package com.example.lib.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KhachHang {
    private int id;
    private String username;
    private String email;
    private String phone;

    public KhachHang(int id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    boolean success;
    String message;
    List<KhachHang> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KhachHang> getResult() {
        return result;
    }

    public void setResult(List<KhachHang> result) {
        this.result = result;
    }
}
