package com.springboot.demo.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String handleMessage(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        return  bindingResult.getAllErrors().stream().map(et -> ((FieldError)et).getField() + " : " + et.getDefaultMessage()).collect(Collectors.joining(","));

    }

}
