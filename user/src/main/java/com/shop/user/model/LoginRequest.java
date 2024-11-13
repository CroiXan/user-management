package com.shop.user.model;

public class LoginRequest {

    private String email;
    private String pasword;

    public LoginRequest() {
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasword() {
        return pasword;
    }
    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
    
}
