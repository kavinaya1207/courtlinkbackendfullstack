package com.example.courtlink.exception;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
