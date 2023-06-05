package com.example.demo1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProductService {
    private final JdbcTemplate jdbcTemplate;

    public void deleteAllTransactionOfProduct(Long idProd) {
        String sql = "DELETE FROM TRANSACTION WHERE IDPROD = ?";
        jdbcTemplate.update(sql, idProd);
    }
}
