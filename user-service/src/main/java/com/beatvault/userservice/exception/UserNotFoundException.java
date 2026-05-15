package com.beatvault.userservice.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Kullanıcı bulunamadı: " + id);
    }

    public UserNotFoundException(String username)
    {
        super("Kullanıcı bulumadı:" +username);
    }
}
