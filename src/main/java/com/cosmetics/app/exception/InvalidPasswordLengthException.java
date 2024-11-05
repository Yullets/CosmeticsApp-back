package com.cosmetics.app.exception;

public class InvalidPasswordLengthException extends RuntimeException {

    public InvalidPasswordLengthException(int length) {
        super("Длина пароля не может превышать более" + length + "символов");
    }
}
