package com.example.courtlink.repository;
    import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.courtlink.entity.AssignmentNotificationEntity;

@Repository
public interface AssignmentNotificationRepository extends JpaRepository<AssignmentNotificationEntity, Long> {
    java.util.List<com.example.courtlink.entity.AssignmentNotificationEntity> findByOwnerEmail(String ownerEmail);
}
