package com.example.courtlink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courtlink.entity.SystemUser;

public interface SystemUserRepo extends JpaRepository<SystemUser,Long> {
    Optional<SystemUser> findByEmail(String email);
} 