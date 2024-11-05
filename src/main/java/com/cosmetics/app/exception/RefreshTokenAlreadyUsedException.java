package com.cosmetics.app.exception;

public class RefreshTokenAlreadyUsedException extends RuntimeException {

    public RefreshTokenAlreadyUsedException(String message) {
        super(message);
    }
}
