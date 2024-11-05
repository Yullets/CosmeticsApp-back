package com.cosmetics.app.service;

import com.cosmetics.app.entity.User;
import com.cosmetics.app.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User save(User user);

    User create(User user);

    User getCurrentUser();

    UserDetailsService userDetailsService();

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    UserDto getCurrentUserDto();
}
