package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.User;
import com.cosmetics.app.exception.NotFoundException;
import com.cosmetics.app.model.UserDto;
import com.cosmetics.app.repository.UserRepository;
import com.cosmetics.app.service.UserService;
import com.cosmetics.app.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new NotFoundException("Пользователь не найден");
        }
        return save(user);
    }

    @Override
    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByEmail(email);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::findByEmail;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto getCurrentUserDto() {
        return userMapper.toDto(getCurrentUser());
    }
}
