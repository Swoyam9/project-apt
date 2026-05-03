package com.ecommerce.models;

public class ResultModel {
    private boolean isSuccess;
    private String message;
    private Object data;
    private String email;
    private String role;
    private String active;

    public ResultModel() {}

    public ResultModel(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public ResultModel(boolean isSuccess, String message, Object data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public ResultModel(boolean isSuccess, String message, String email, String role, String active) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    // Getters and Setters
    public boolean isSuccess() { return isSuccess; }
    public void setSuccess(boolean success) { isSuccess = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getActive() { return active; }
    public void setActive(String active) { this.active = active; }
}