package com.cosmetics.app.service;

import com.cosmetics.app.model.SignInRequest;
import com.cosmetics.app.model.TokensPair;
import com.cosmetics.app.model.UserRegistrationData;

public interface AuthService {

    void signUp(UserRegistrationData userInputData);

    TokensPair signIn(SignInRequest request);
}
