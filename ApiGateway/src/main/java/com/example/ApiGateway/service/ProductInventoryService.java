package com.example.ApiGateway.service;

import com.example.ApiGateway.model.ProductCompany;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductInventoryService {
    private final RestTemplate restTemplate;

    public List<ProductCompany> getCompanyProducts() {
        List<ProductCompany> productsCA = restTemplate.exchange("http://localhost:8080/ca/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductCompany>>() {
        }).getBody();
        List<ProductCompany> productsCB = restTemplate.exchange("http://localhost:8080/cb/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductCompany>>() {
        }).getBody();
        assert productsCA != null && productsCB != null;
        productsCA.forEach(System.out::println);
        productsCB.forEach(System.out::println);
        List<ProductCompany> productsCompany = new ArrayList<>(productsCA);
        productsCompany.addAll(productsCB);
        return productsCompany;
    }
}
