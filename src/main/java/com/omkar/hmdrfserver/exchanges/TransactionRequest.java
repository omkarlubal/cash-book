package com.omkar.hmdrfserver.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private String sender;
    private String recipient;
    private double amount;
    private String note;
}
