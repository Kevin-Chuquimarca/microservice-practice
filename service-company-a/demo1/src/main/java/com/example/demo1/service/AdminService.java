package com.example.demo1.service;

import com.example.demo1.entity.Useradmin;
import com.example.demo1.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public boolean loginAdmin(String user, String password) {
        System.out.println("Admins: ");
        adminRepository.findAll().forEach(System.out::println);
        Useradmin  admin = adminRepository.findById(user).orElse(new Useradmin("", ""));
        return admin.getPassaccount().equals(password) && admin.getUseraccount().equals(user);
    }
}
