package com.ott.onde.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(com.ott.onde.util.HospitalReviewAppException.class)
    public ResponseEntity<?> hospitalReviewAppExceptionHandler(com.ott.onde.util.HospitalReviewAppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(com.ott.onde.util.Response.error(e.getErrorCode().getMessage()));
    }
}