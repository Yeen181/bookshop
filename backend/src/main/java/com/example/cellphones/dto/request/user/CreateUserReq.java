package com.example.cellphones.dto.request.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserReq {
    private String username;
    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;
    private String address;

    public CreateUserReq() {
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
