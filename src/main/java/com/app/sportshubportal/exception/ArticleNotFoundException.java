package com.app.sportshubportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends BusinessException {
    public ArticleNotFoundException(String s) {
        super(s);
    }
}
