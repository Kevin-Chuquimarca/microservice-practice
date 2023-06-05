package com.example.demo1.service;

import com.example.demo1.entity.Transaction;
import com.example.demo1.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> listTransaction(){
        return transactionRepository.findAll();
    }

    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id){
        transactionRepository.deleteById(id);
    }
}
