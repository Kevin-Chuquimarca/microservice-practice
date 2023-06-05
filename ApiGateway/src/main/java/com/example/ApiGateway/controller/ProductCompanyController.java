package com.example.ApiGateway.controller;

import com.example.ApiGateway.model.ProductCompany;
import com.example.ApiGateway.service.ProductInventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-company")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ProductCompanyController {
    private final ProductInventoryService productInventory;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCompany> get() {
        return productInventory.getCompanyProducts();
    }
}
