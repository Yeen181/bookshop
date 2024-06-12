package com.example.cellphones.controller;

import com.example.cellphones.dto.request.user.CreateUserReq;
import com.example.cellphones.dto.request.user.SetRoleReq;
import com.example.cellphones.dto.request.user.UpdateUserReq;
import com.example.cellphones.model.user.User;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/info")
    public ResponseEntity<?> getUserInfo() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.getUserInfo(currentUser.getId()))
                        .build()
        );
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.getUsers())
                        .build()
        );
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserReq req) {
        boolean res = userService.createUser(req);
        if(res)
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Gửi yêu cầu tài khoản thành công")
                            .build());
        else
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Gửi yêu cầu tài khoản thất bại")
                            .build());
    }

    @PostMapping(path = "/active/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> activeAccount(@PathVariable String username) {
        boolean res = userService.activeAccount(username);
        if(res)
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Kích hoạt tài khoản thành công")
                            .build());
        else
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Kích hoạt tài khoản thất bại")
                            .build());
    }

    @PostMapping(path = "/inactive/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> inactiveAccount(@PathVariable String username) {
        boolean res = userService.inactiveAccount(username);
        if(res)
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Hủy kích hoạt tài khoản thành công")
                            .build());
        else
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Hủy kích hoạt tài khoản thất bại")
                            .build());
    }

    @PostMapping(path = "/setRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setRole(@RequestBody SetRoleReq req) {
        boolean res = userService.setRoleForUser(req.getUsername(), req.getRole());
        if(res)
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data("Thiết lập vai trò thành công")
                    .build());
        else
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data("Thiết lập vai trò thất bại")
                    .build());
    }

    @PostMapping(path = "/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserReq req, @PathVariable String userId) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.updateUser(req, Long.parseLong(userId)))
                        .build()
        );
    }

    @DeleteMapping(path = "/delete/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        boolean res = userService.deleteUser(username);
        if(res)
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data("Xóa tài khoản thành công")
                    .build());
        else
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data("Xóa tài khoản thất bại")
                    .build());
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchProduct(@Param("searchText") String searchText,
                                           @Param("active") Boolean active,
                                           @Param("page") Integer page,
                                           @Param("limit") Integer limit,
                                           @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userService.searchUser(searchText, active, page, limit, isPaginate))
                .build());
    }
}
