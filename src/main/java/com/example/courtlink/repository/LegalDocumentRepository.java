package com.example.courtlink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.courtlink.entity.LegalDocumentEntity;

@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocumentEntity, Long> {
    java.util.List<com.example.courtlink.entity.LegalDocumentEntity> findByOwnerEmail(String ownerEmail);
}