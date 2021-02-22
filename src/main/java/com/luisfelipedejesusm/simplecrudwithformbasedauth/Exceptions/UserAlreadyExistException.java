package com.luisfelipedejesusm.simplecrudwithformbasedauth.Exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
