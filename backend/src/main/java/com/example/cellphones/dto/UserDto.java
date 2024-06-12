package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private String birthday;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String role;
    private Boolean active;
}
