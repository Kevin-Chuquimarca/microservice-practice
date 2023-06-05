package com.example.demo1.repository;

import com.example.demo1.entity.Useradmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Useradmin, String> {
}
