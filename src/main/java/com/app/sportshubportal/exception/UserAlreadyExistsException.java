package com.app.sportshubportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String username) {
        super("User with username:" + username + " already exists!");
    }

}
