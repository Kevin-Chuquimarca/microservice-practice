package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name="PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPROD", nullable = false, precision = 0)
    private Long idprod;
    @Basic
    @Column(name = "NAMEPROD", nullable = true, length = 20)
    private String nameprod;
    @Basic
    @Column(name = "QUANTITYPROD", nullable = true, precision = 0)
    private Long quantityprod;
    @Basic
    @Column(name = "PRICEPROD", nullable = true, precision = 0)
    private Double priceprod;
    @Basic
    @Column(name = "COMPANYPROD", nullable = true, length = 1)
    private String companyprod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idprod, product.idprod) && Objects.equals(nameprod, product.nameprod) && Objects.equals(quantityprod, product.quantityprod) && Objects.equals(priceprod, product.priceprod) && Objects.equals(companyprod, product.companyprod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idprod, nameprod, quantityprod, priceprod, companyprod);
    }
}
