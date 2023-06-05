package com.example.demo1.controller;

import com.example.demo1.entity.Transaction;
import com.example.demo1.service.TransactionProductService;
import com.example.demo1.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ca")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionProductService transactionProductService;

    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions() {
        return transactionService.listTransaction();
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@RequestBody Transaction transaction) {
        transactionService.saveTransaction(transaction);
    }

    @DeleteMapping("/transaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
    }
}
