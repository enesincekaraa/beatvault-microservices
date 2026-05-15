package com.beatvault.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String field, String value) {
        super(field + " zaten kullanımda: " + value);
    }
}
