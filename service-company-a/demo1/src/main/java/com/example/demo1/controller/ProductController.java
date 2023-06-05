package com.example.demo1.controller;

import com.example.demo1.entity.Product;
import com.example.demo1.service.ProductService;
import com.example.demo1.service.TransactionProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ca")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
//@CircuitBreaker(name = "backendA", fallbackMethod = "fallbackMethodProducts")
public class ProductController {
    private final ProductService productService;
    private final TransactionProductService transactionProductService;

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productService.listProducts();
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void postProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PutMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setIdprod(id);
        productService.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("id") Long id) {
        transactionProductService.deleteAllTransactionOfProduct(id);
        productService.deleteProduct(id);
    }

//    @GetMapping("/fallbackMethodTest")
//    public String get() {
//        throw new RuntimeException("Error occurred while fetching products");
//    }
//
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public String fallbackMethodProducts(Throwable throwable) {
//        return "circuit breaker info: demo 1";
//    }
}
