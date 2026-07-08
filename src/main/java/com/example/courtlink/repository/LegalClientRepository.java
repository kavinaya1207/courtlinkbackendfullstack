package com.example.courtlink.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courtlink.entity.LegalClientEntity;

public interface LegalClientRepository extends JpaRepository<LegalClientEntity, Long> {
    java.util.List<com.example.courtlink.entity.LegalClientEntity> findByOwnerEmail(String ownerEmail);
}
