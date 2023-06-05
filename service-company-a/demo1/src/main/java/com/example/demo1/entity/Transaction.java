package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name="TRANSACTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDTRANS", nullable = false, precision = 0)
    private Long idtrans;
    @Basic
    @Column(name = "NAMETRANS", nullable = true, length = 30)
    private String nametrans;
    @Basic
    @Column(name = "TYPETRANS", nullable = true, length = 30)
    private String typetrans;
    @Basic
    @Column(name = "OBSTRANS", nullable = true, length = 150)
    private String obstrans;
    @ManyToOne
    @JoinColumn(name = "IDPROD", referencedColumnName = "IDPROD")
    private Product productByIdprod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(idtrans, that.idtrans) &&  Objects.equals(nametrans, that.nametrans) && Objects.equals(typetrans, that.typetrans) && Objects.equals(obstrans, that.obstrans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtrans, nametrans, typetrans, obstrans);
    }

    public void setProductByIdprod(Product productByIdprod) {
        this.productByIdprod = productByIdprod;
    }
}
