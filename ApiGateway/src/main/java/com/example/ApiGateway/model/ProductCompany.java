package com.example.ApiGateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCompany {
    private Long idprod;
    private String nameprod;
    private Long quantityprod;
    private float priceprod;
    private char companyprod;
}
