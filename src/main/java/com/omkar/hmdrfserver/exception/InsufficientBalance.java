package com.omkar.hmdrfserver.exception;

public class InsufficientBalance extends RuntimeException {
    public InsufficientBalance() {
    }

    public InsufficientBalance(String message) {
        super(message);
    }
}
