package com.example.courtlink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.courtlink.entity.LegalCaseEntity;
import com.example.courtlink.service.LegalCaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/legal/api")
public class LegalCaseController {

    @Autowired
    private LegalCaseService legalCaseService;

    @PostMapping("/postData")
    public LegalCaseEntity saveData(@Valid @RequestBody LegalCaseEntity data) {
        return legalCaseService.saveData(data);
    }

    @GetMapping("/dom")
    public List<LegalCaseEntity> getData() {
        return legalCaseService.getAllData();
    }

    @GetMapping("/getData/{id}")
    public LegalCaseEntity getCase(@PathVariable Long id) {
        return legalCaseService.getUserDetails(id);
    }

    @GetMapping("/Data/{id}")
    public ResponseEntity<?> getCaseData(@PathVariable Long id) {
        try {
            LegalCaseEntity getData = legalCaseService.getUserDetails(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Case not found");
        }
    }

    @PutMapping("/lev/{id}")
    public LegalCaseEntity updateData(
            @PathVariable Long id,
            @RequestBody LegalCaseEntity data) {

        return legalCaseService.updateDatabase(id, data);
    }

    @DeleteMapping("/mep/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            LegalCaseEntity getData = legalCaseService.getDelete(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Case not found");
        }
    }

    @PatchMapping("/patchfav/{id}")
    public LegalCaseEntity patchUserdata(
            @PathVariable Long id,
            @RequestBody LegalCaseEntity data) {

        return legalCaseService.patchUser(id, data);
    }
}