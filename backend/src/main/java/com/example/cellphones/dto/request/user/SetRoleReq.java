package com.example.cellphones.dto.request.user;

import com.example.cellphones.model.user.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SetRoleReq {
    private String username;
    private Role role;
}
