package com.example.cellphones.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
//    MANAGER_READ("management:read"),
//    MANAGER_UPDATE("management:update"),
//    MANAGER_CREATE("management:create"),
//    MANAGER_DELETE("management:delete")
    ;

    @Getter
    private final String permission; // gán giá trị bằng tham số truyền vào từ constructor

    public static void main(String[] args) {
        Permission permission = Permission.ADMIN_CREATE;
        System.out.println(permission.getPermission());
        System.out.println(permission.name());
        System.out.println(permission);
    }
}