package com.ott.ond.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(com.ott.ond.util.HospitalReviewAppException.class)
    public ResponseEntity<?> hospitalReviewAppExceptionHandler(com.ott.ond.util.HospitalReviewAppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(com.ott.ond.util.Response.error(e.getErrorCode().getMessage()));
    }
}