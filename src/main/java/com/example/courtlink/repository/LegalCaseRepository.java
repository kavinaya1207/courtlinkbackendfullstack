package com.example.courtlink.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courtlink.entity.LegalCaseEntity;

public interface LegalCaseRepository extends JpaRepository<LegalCaseEntity, Long> {
    java.util.List<com.example.courtlink.entity.LegalCaseEntity> findByOwnerEmail(String ownerEmail);
}
