package com.example.courtlink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.courtlink.entity.AssignmentNotificationEntity;
import com.example.courtlink.repository.AssignmentNotificationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AssignmentNotificationService {

    @Autowired
    private AssignmentNotificationRepository assignmentNotificationRepository;

    // Helper to get current user details
    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // Save
    public AssignmentNotificationEntity save(AssignmentNotificationEntity notification) {
        notification.setOwnerEmail(getCurrentUserEmail());
        return assignmentNotificationRepository.save(notification);
    }

    // Get All
    public List<AssignmentNotificationEntity> getAll() {
        if (isAdmin()) {
            return assignmentNotificationRepository.findAll();
        } else {
            return assignmentNotificationRepository.findByOwnerEmail(getCurrentUserEmail());
        }
    }

    // Get By Id
    public AssignmentNotificationEntity getDataById(Long id) {
        AssignmentNotificationEntity data = assignmentNotificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Assignment Notification Not Found with id: " + id));
                        
        if (!isAdmin() && !data.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return data;
    }

    // Update (PUT)
    public AssignmentNotificationEntity updateData(Long id, AssignmentNotificationEntity notification) {

        AssignmentNotificationEntity assignment = assignmentNotificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Assignment Notification Not Found"));
                        
        if (!isAdmin() && !assignment.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        assignment.setCaseName(notification.getCaseName());
        assignment.setFileName(notification.getFileName());
        assignment.setFileType(notification.getFileType());
        assignment.setStorageUrl(notification.getStorageUrl());
        assignment.setUploadDate(notification.getUploadDate());
        assignment.setLegalCaseId(notification.getLegalCaseId());

        return assignmentNotificationRepository.save(assignment);
    }

    // Delete
    public AssignmentNotificationEntity getDelete(Long id) {

        AssignmentNotificationEntity assignment = assignmentNotificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Assignment Notification Not Found"));
                        
        if (!isAdmin() && !assignment.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        assignmentNotificationRepository.delete(assignment);

        return assignment;
    }

    // Patch
    public AssignmentNotificationEntity patchUser(Long id, AssignmentNotificationEntity data) {

        AssignmentNotificationEntity assignment = assignmentNotificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Assignment Notification Not Found"));
                        
        if (!isAdmin() && !assignment.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        if (data.getCaseName() != null)
            assignment.setCaseName(data.getCaseName());

        if (data.getFileName() != null)
            assignment.setFileName(data.getFileName());

        if (data.getFileType() != null)
            assignment.setFileType(data.getFileType());

        if (data.getStorageUrl() != null)
            assignment.setStorageUrl(data.getStorageUrl());

        if (data.getUploadDate() != null)
            assignment.setUploadDate(data.getUploadDate());

        if (data.getLegalCaseId() != 0)
            assignment.setLegalCaseId(data.getLegalCaseId());

        return assignmentNotificationRepository.save(assignment);
    }
}