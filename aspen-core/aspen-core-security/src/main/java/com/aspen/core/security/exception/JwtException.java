package com.aspen.core.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtException extends AuthenticationException {

    public JwtException(String msg) {
        super(msg);
    }
}
