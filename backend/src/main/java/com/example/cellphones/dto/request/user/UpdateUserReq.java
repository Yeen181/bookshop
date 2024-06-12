package com.example.cellphones.dto.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserReq {
    private String fullName;
    private String birthday;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
}
