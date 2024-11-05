package com.cosmetics.app.controller.impl;

import com.cosmetics.app.controller.UserController;
import com.cosmetics.app.model.UserDto;
import com.cosmetics.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserDto> getProfileData() {
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }
}
