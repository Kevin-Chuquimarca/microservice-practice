package com.example.demo1.controller;

import com.example.demo1.dto.LoginResponse;
import com.example.demo1.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ca")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LoginResponse login(@RequestBody Map<String, String> json) {
        return new LoginResponse(adminService.loginAdmin(json.get("username"), json.get("password")));
    }
}
