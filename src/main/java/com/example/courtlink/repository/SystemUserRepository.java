package com.example.courtlink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courtlink.entity.SystemUserEntity;

public interface SystemUserRepository  extends JpaRepository<SystemUserEntity, Long> {}
    

