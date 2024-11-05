package com.cosmetics.app.controller.impl;

import com.cosmetics.app.controller.AuthController;
import com.cosmetics.app.model.RefreshRequestParams;
import com.cosmetics.app.model.SignInRequest;
import com.cosmetics.app.model.TokensPair;
import com.cosmetics.app.model.UserRegistrationData;
import com.cosmetics.app.service.AuthService;
import com.cosmetics.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(UserRegistrationData request) {
        authService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<TokensPair> signIn(SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<TokensPair> refreshAuthToken(RefreshRequestParams body) {
        return ResponseEntity.ok(jwtService.refreshAuthToken(body.getRefreshToken()));
    }
}
