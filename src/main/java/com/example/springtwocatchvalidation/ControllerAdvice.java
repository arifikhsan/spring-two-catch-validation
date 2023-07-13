package com.example.springtwocatchvalidation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        System.out.println(bodyOfResponse);
        var errorMap = new HashMap<String, ArrayList<String>>();

        for (FieldError f : ex.getFieldErrors()) {
            var key = f.getField();
            var error = f.getDefaultMessage();

            if (errorMap.containsKey(key)) {
                errorMap.get(key).add(error);
            } else {
                errorMap.put(key, new ArrayList<>(Collections.singleton(f.getField() + " " + error)));
            }
        }

        List<FieldErrorResponse> dto = errorMap.entrySet()
                .stream()
                .map(error -> new FieldErrorResponse(error.getKey(), error.getValue()))
                .toList();
        return ResponseEntity.badRequest().body(dto);
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
