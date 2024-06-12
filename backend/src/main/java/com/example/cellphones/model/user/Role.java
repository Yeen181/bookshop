package com.example.cellphones.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Collections.emptySet()),
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                .map(permission ->
                        new SimpleGrantedAuthority(permission.getPermission())
                )
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public static Boolean checkRoleValid(String role) {
        return role.equals(Role.ADMIN.name()) || role.equals(Role.USER.name());
    }
    public static void main(String[] args) {
        Role admin = Role.ADMIN;
        System.out.println(admin.name());
        admin.getAuthorities().forEach(System.out::println);
        System.out.println("---------------------------------");
        admin.getPermissions().forEach(System.out::println);
        System.out.println(admin);
    }
}
