package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "USERADMIN")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Useradmin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USERACCOUNT", nullable = false, length = 30)
    private String useraccount;
    @Basic
    @Column(name = "PASSACCOUNT", nullable = true, length = 30)
    private String passaccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Useradmin useradmin = (Useradmin) o;
        return Objects.equals(useraccount, useradmin.useraccount) && Objects.equals(passaccount, useradmin.passaccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(useraccount, passaccount);
    }
}
