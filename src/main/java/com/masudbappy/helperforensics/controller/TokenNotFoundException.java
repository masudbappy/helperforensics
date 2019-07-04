package com.masudbappy.helperforensics.controller;

import com.masudbappy.helperforensics.exceptions.TokenException;

public class TokenNotFoundException extends TokenException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
