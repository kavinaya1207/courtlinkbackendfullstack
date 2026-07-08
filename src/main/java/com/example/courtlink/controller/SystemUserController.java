package com.example.courtlink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.courtlink.entity.SystemUserEntity;
import com.example.courtlink.service.SystemUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class SystemUserController {

    @Autowired
    SystemUserService stuSer;
    @Autowired
     private PasswordEncoder passwordEncoder;

    @PostMapping("/postData")
    public SystemUserEntity saveData(@Valid@RequestBody SystemUserEntity data) {
        data.setPassword(passwordEncoder.encode(data.getPassword()));
        return stuSer.saveData(data);
    }

    @PostMapping("/get/gmail")
     @PreAuthorize("hasRole('ADMIN')")
    public String generateTokens(@RequestParam ("mail")String gmail){
        return stuSer.generateToken(gmail);
    }

    @GetMapping("/dom")
    public List<SystemUserEntity> getData() {
        return stuSer.getAllData();
    }

    @GetMapping("/getData/{id}")
    public SystemUserEntity getUser(@PathVariable Long id) {
        return stuSer.getDataById(id);
    }

    @GetMapping("/Data/{id}")
    public ResponseEntity<?> getUserData(@PathVariable Long id) {
        try {
            SystemUserEntity getData = stuSer.getUserDetails(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    @PutMapping("/lev/{id}")
    public SystemUserEntity updateData(
            @PathVariable Long id,
            @RequestBody SystemUserEntity data) {

        return stuSer.updateDatabase(id, data);
    }

    @DeleteMapping("/mep/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            SystemUserEntity getData = stuSer.getDelete(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }
    @PatchMapping("/patchfav/{id}")
public SystemUserEntity patchUserdata(
        @PathVariable Long id,
        @RequestBody SystemUserEntity data) {

    return stuSer.patchUser(id, data);
}
}