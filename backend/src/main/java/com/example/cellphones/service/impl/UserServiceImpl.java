package com.example.cellphones.service.impl;

import com.example.cellphones.dto.UserDto;
import com.example.cellphones.dto.request.user.CreateUserReq;
import com.example.cellphones.dto.request.user.UpdateUserReq;
import com.example.cellphones.exception.UserNotFoundByIdException;
import com.example.cellphones.exception.UserNotFoundByUsername;
import com.example.cellphones.mapper.UserMapper;
import com.example.cellphones.model.Cart;
import com.example.cellphones.model.user.Gender;
import com.example.cellphones.model.user.Role;
import com.example.cellphones.model.user.User;
import com.example.cellphones.repository.UserRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepo;

    final private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = this.userRepo.findAll();
        return (users.stream().map(UserMapper::responseUserDtoFromModel).collect(Collectors.toList()));
    }

    @Override
    public UserDto getUserInfo(Long id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
        return (UserMapper.responseUserDtoFromModel(user));
    }

    @Override
    public boolean createUser(CreateUserReq request) {
        try {

            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .enabled(true)
                    .build();
            user.setCart(Cart.builder()
                    .user(user)
                    .build());
            this.userRepo.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean activeAccount(String username) {
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
            user.setEnabled(true);
            this.userRepo.saveAndFlush(user);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean inactiveAccount(String username) {
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
            user.setEnabled(false);
            this.userRepo.saveAndFlush(user);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean setRoleForUser(String username, Role role) {
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
            user.setRole(role);
            this.userRepo.saveAndFlush(user);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public UserDto updateUser(UpdateUserReq request, Long userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundByIdException(userId));

        user.setAddress(request.getAddress());
        user.setBirthday(request.getBirthday());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setGender(Gender.valueOf(request.getGender()));
        user.setPhoneNumber(request.getPhoneNumber());
        user = this.userRepo.save(user);
        return (UserMapper.responseUserDtoFromModel(user));
    }

    @Override
    public boolean deleteUser(String username) {
        try {
            User user = this.userRepo.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundByUsername(username));
            user.getCart().setUser(null);
            user.setEnabled(false);
            this.userRepo.save(user);
//            this.userRepo.deleteUserByUsername(username);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Object searchUser(String searchText, Boolean active, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<User> users = this.userRepo.getPaginate(searchText, active, pageable);
            return PageResult.builder()
                    .data(users.stream()
                            .map(UserMapper::responseUserDtoFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(users.getNumber())
                    .limit(limit)
                    .totalItems(users.getTotalElements())
                    .totalPages(users.getTotalPages())
                    .build();
        }
        return this.userRepo.findAll().stream()
                .map(UserMapper::responseUserDtoFromModel)
                .collect(Collectors.toList());
    }

    protected Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 20 : size;
        sortBy = sortBy == null || sortBy.isEmpty() ? "id" : sortBy;
        Sort.Direction sortOrderRequest = sortOrder == null || sortOrder.isEmpty() ? Sort.Direction.ASC : Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(sortOrderRequest, sortBy);
        return PageRequest
                .of(page, size, sort);
    }
}
