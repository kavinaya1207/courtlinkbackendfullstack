package com.example.courtlink.service;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.courtlink.entity.SystemUserEntity;
import com.example.courtlink.repository.SystemUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class SystemUserService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private SystemUserRepository stuRepo;

    public SystemUserEntity saveData(SystemUserEntity data) {
        return stuRepo.save(data);
    }

    public List<SystemUserEntity> getAllData() {
        return stuRepo.findAll();
    }

    public SystemUserEntity getDataById(Long id) {
        return stuRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User Not Found with id: " + id));
    }

    public SystemUserEntity getUserDetails(Long id) {
        return stuRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User Not Found"));
    }

    public SystemUserEntity updateDatabase(Long id, SystemUserEntity data) {

        SystemUserEntity user = stuRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User Not Found to Update"));

        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());

        return stuRepo.save(user);
    }

    public SystemUserEntity getDelete(Long id) {

        SystemUserEntity user = stuRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User Not Found to Delete"));

        stuRepo.delete(user);
        return user;
    }

   
    public SystemUserEntity patchUser(Long id, SystemUserEntity data) {

        SystemUserEntity user = stuRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User Not Found"));

        if (data.getUsername() != null)
            user.setUsername(data.getUsername());

        if (data.getEmail() != null)
            user.setEmail(data.getEmail());

        if (data.getPassword() != null)
            user.setPassword(data.getPassword());

        return stuRepo.save(user);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getKeys())
                .compact();
    }

    private Key getKeys() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}