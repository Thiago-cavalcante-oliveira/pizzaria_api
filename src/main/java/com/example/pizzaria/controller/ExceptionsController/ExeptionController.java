package com.example.pizzaria.controller.ExceptionsController;

import com.example.pizzaria.ErrorHandler.ErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
@RestControllerAdvice
public class ExeptionController {
    @ResponseStatus(HttpStatus.CONFLICT) @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorHandler> handler(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();

        List<ErrorHandler> errorHandleList = new ArrayList<>();
        fieldErrorList.forEach(error -> errorHandleList.add(new ErrorHandler(error.getField(), error.getDefaultMessage())));

        return errorHandleList;
    }
}
