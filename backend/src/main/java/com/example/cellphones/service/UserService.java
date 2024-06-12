package com.example.cellphones.service;

import com.example.cellphones.dto.UserDto;
import com.example.cellphones.dto.request.user.CreateUserReq;
import com.example.cellphones.dto.request.user.UpdateUserReq;
import com.example.cellphones.model.user.Role;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserInfo(Long id);
    boolean createUser(CreateUserReq request);

    boolean activeAccount(String username);

    boolean inactiveAccount(String username);

    boolean setRoleForUser(String username, Role role);
    UserDto updateUser(UpdateUserReq request, Long userId);
    boolean deleteUser(String username);

    Object searchUser(String searchText, Boolean active, Integer page, Integer limit, Boolean isPaginate);
}
