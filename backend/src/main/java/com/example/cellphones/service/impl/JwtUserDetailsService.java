package com.example.cellphones.service.impl;

import com.example.cellphones.exception.UserNotFoundByUsername;
import com.example.cellphones.model.user.User;
import com.example.cellphones.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    final private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsername(username));
        return user;
    }
}