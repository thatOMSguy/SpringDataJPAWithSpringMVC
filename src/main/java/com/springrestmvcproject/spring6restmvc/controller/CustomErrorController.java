package com.springrestmvcproject.spring6restmvc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//global exception handler
//not needed howevr for our case unused in this project
@ControllerAdvice
public class CustomErrorController {

   /*@ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFountException() {
        System.out.println( "\n------------------------------------------\nHandling Not Found Exception\n-------------------------------------------");
        return ResponseEntity.notFound().build();
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindingExceptions(MethodArgumentNotValidException exception) {
        System.out.println("\n------------------------------------------\nHandling " +
                "MethodArgumentNotValidException\n-------------------------------------------");

        List errorList = exception.getFieldErrors().stream().map(
                fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());


    return ResponseEntity.badRequest().body(errorList);

    }


}
