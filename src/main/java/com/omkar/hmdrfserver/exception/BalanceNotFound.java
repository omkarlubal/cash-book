package com.omkar.hmdrfserver.exception;

import java.util.function.Supplier;

public class BalanceNotFound extends RuntimeException{

    public BalanceNotFound() {}

    public BalanceNotFound(String msg) {
        super(msg);
    }
}
