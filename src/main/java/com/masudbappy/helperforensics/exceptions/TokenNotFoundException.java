package com.masudbappy.helperforensics.exceptions;

public class TokenNotFoundException extends TokenException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
