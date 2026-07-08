package com.example.courtlink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.courtlink.entity.CaseHearingEntity;

@Repository
public interface CaseHearingRepository extends JpaRepository<CaseHearingEntity, Long> {
    java.util.List<com.example.courtlink.entity.CaseHearingEntity> findByOwnerEmail(String ownerEmail);
}
