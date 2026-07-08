package com.example.courtlink.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.courtlink.entity.AssignmentNotificationEntity;
import com.example.courtlink.service.AssignmentNotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/assign/api")
public class AssignmentNotificationController {

    @Autowired
    private AssignmentNotificationService assignmentNotificationService;

    @PostMapping("/postData")
    public AssignmentNotificationEntity saveData(@Valid @RequestBody AssignmentNotificationEntity data) {
        return assignmentNotificationService.save(data);
    }

    @GetMapping("/dom")
    public List<AssignmentNotificationEntity> getData() {
        return assignmentNotificationService.getAll();
    }

    @GetMapping("/getData/{id}")
    public AssignmentNotificationEntity getAssignment(@PathVariable Long id) {
        return assignmentNotificationService.getDataById(id);
    }

    @GetMapping("/Data/{id}")
    public ResponseEntity<?> getAssignmentData(@PathVariable Long id) {
        try {
            AssignmentNotificationEntity getData = assignmentNotificationService.getDataById(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Assignment not found");
        }
    }

    @PutMapping("/lev/{id}")
    public AssignmentNotificationEntity updateData(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentNotificationEntity data) {

        return assignmentNotificationService.updateData(id, data);
    }

    @DeleteMapping("/mep/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            AssignmentNotificationEntity getData = assignmentNotificationService.getDelete(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Assignment not found");
        }
    }

    @PatchMapping("/patchfav/{id}")
    public AssignmentNotificationEntity patchUserdata(
            @PathVariable Long id,
            @RequestBody AssignmentNotificationEntity data) {

        return assignmentNotificationService.patchUser(id, data);
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}