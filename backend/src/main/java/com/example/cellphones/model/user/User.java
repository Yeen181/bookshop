package com.example.cellphones.model.user;

import javax.persistence.*;

import com.example.cellphones.model.Cart;
import com.example.cellphones.model.Order;
import com.example.cellphones.model.Product;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 30, nullable = false, unique = true)
    private String username;

    @Column( nullable = false, unique = true)
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullName;

    private String birthday;

    private String email;

//    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8")
    private String address;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private List<Order> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
