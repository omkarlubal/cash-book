package com.omkar.hmdrfserver.controller;

import com.omkar.hmdrfserver.exchanges.ApiResponse;
import com.omkar.hmdrfserver.exchanges.BalanceResponse;
import com.omkar.hmdrfserver.exchanges.TransactionRequest;
import com.omkar.hmdrfserver.model.Balance;
import com.omkar.hmdrfserver.model.Transaction;
import com.omkar.hmdrfserver.security.JwtTokenUtil;
import com.omkar.hmdrfserver.service.BalanceService;
import com.omkar.hmdrfserver.service.TransactionService;
import com.omkar.hmdrfserver.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * transfer funds
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("/api/funds/")
public class FundsController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransactionRequest tRequest) {
        ApiResponse response = isTxnValid(tRequest);
        log.info("Transaction request received : {}", tRequest.toString());
        if (response.isSuccess()) {
            return ResponseEntity.ok(makeTransfer(tRequest));
        } else {
            return ResponseEntity.ok(response);
        }
    }

//    /**
//     * In case we have to send funds to a new person, then create
//     * balance account for that person first and then transfer the funds.
//     * @param tRequest
//     * @return
//     */
//    @Deprecated
//    @PostMapping("/outsideTransfer")
//    public ResponseEntity<?> transferFundsToNewPerson(@RequestBody TransactionRequest tRequest) {
//        ApiResponse response = isTxnValid(tRequest);
//        balanceService.createBalance(tRequest.getRecipient(), 0);
//        if (response.isSuccess()) {
//            return ResponseEntity.ok(makeTransfer(tRequest));
//        } else {
//            return ResponseEntity.ok(response);
//        }
//    }

    @GetMapping("hmbal/get")
    public ResponseEntity<?> getHMBal(@RequestHeader("Authorization") String token) {
        log.info("Call Get HMBAL for user : {}",jwtTokenUtil.getUsernameFromToken(token.substring(7)));
        Balance balance = balanceService.getBalance(Constants.HMDRF_BALANCE_NAME);
        return ResponseEntity.ok(new BalanceResponse(true, Constants.HMDRF_BALANCE_NAME, balance.getBalance()));
    }

    @GetMapping("transactions/getAll")
    public ResponseEntity<?> getAllTransactions(@RequestHeader("Authorization") String token) {
        log.info("Call Get Transactions for user : {}",jwtTokenUtil.getUsernameFromToken(token.substring(7)));
        List<Transaction> transactions = transactionService.getAllTransactions();
        Map<String, Object> response = new HashMap<>();
        response.put("success",true);
        response.put("transactions",transactions);
        return ResponseEntity.ok(response);
    }

    private ApiResponse makeTransfer(TransactionRequest tRequest) {
        balanceService.transferFunds(tRequest);
        transactionService.save(tRequest);
        return new ApiResponse(true, "SUCCESSFUL TRANSFER");
    }

    private ApiResponse isTxnValid(TransactionRequest request) {
        if (request.getSender().length() < 3) {
            return new ApiResponse(false, "Sender Name can't be less than 3 characters");
        }

        if (request.getRecipient().length() < 3) {
            return new ApiResponse(false, "Recipient Name can't be less than 3 characters");
        }

        if (request.getRecipient().equalsIgnoreCase(request.getSender())) {
            return new ApiResponse(false, "Recipient and Sender is same. Change it!");
        }

        if (request.getNote().length() < 5) {
            return new ApiResponse(false, "Note can't be less than 5 characters");
        }

        double amt= request.getAmount();
        if (amt <= 0 ) {
            return new ApiResponse(false, "Amount can't be less than or equal to 0");
        }

        if ( BigDecimal.valueOf(amt).scale() > 2 ) {
            return new ApiResponse(false, "Amount decimal places are more than 2. Not Allowed.");
        }

        return new ApiResponse(true, "All cool!");
    }

}
