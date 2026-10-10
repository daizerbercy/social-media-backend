package com.daizer.social_media_backend;

import com.daizer.social_media_backend.user.DuplicateUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DuplicateUserException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage handleDuplicateUserException(DuplicateUserException e){
        ErrorMessage message = new ErrorMessage(new Date(),HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.name(),e.getMessage());
        return message;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handle(IllegalArgumentException e) {
        ErrorMessage message = new ErrorMessage(new Date(),HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name(),e.getMessage());
        return message;
    }
}
