package com.maarketplace.exception;

public class UserCredentialsUsernameNotExistsException extends RuntimeException {
    public UserCredentialsUsernameNotExistsException() {
        super();
    }

    public UserCredentialsUsernameNotExistsException(String message) {
        super(message);
    }
}
