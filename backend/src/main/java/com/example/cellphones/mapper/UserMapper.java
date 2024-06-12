package com.example.cellphones.mapper;

import com.example.cellphones.dto.UserDto;
import com.example.cellphones.model.user.User;

public class UserMapper {
    public static UserDto responseUserDtoFromModel(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .gender(String.valueOf(user.getGender()))
                .role(String.valueOf(user.getRole()))
                .active(user.isEnabled())
                .build();
    }
}
