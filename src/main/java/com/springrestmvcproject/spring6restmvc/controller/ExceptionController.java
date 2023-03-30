package com.springrestmvcproject.spring6restmvc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//global exception handler
//not needed howevr for our case unused in this project
//@ControllerAdvice
public class ExceptionController {

   // @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFountException() {
        System.out.println( "\n------------------------------------------\nHandling Not Found Exception\n-------------------------------------------");
        return ResponseEntity.notFound().build();
    }

}
