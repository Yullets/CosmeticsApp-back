package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.User;
import com.cosmetics.app.exception.InvalidPasswordLengthException;
import com.cosmetics.app.model.SignInRequest;
import com.cosmetics.app.model.TokensPair;
import com.cosmetics.app.model.UserRegistrationData;
import com.cosmetics.app.service.AuthService;
import com.cosmetics.app.service.JwtService;
import com.cosmetics.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final int MAX_PASSWORD_LENGTH = 20;

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserRegistrationData userInputData) {
        if (userInputData.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidPasswordLengthException(MAX_PASSWORD_LENGTH);
        }

        User user = createUser(userInputData);
        userService.create(user);
    }

    @Override
    public TokensPair signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));
        var user = userService.userDetailsService().loadUserByUsername(request.getLogin());
        var jwt = jwtService.generateToken(user);
        return new TokensPair(jwt.getAccessToken(), jwt.getRefreshToken());
    }

    private User createUser(UserRegistrationData userRegistrationData) {
        return User.builder()
                .fullName(userRegistrationData.getFullName())
                .email(userRegistrationData.getEmail())
                .password(passwordEncoder.encode(userRegistrationData.getPassword()))
                .build();
    }
}
