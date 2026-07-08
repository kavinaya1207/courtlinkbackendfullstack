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

import com.example.courtlink.entity.CaseHearingEntity;
import com.example.courtlink.service.CaseManagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hearing/api")
public class CaseHearingController {

    @Autowired
    private CaseManagementService caseManagementService;

    @PostMapping("/postData")
    public CaseHearingEntity saveData(@Valid @RequestBody CaseHearingEntity data) {
        return caseManagementService.saveData(data);
    }

    @GetMapping("/dom")
    public List<CaseHearingEntity> getData() {
        return caseManagementService.getAllData();
    }

    @GetMapping("/getData/{id}")
    public CaseHearingEntity getHearing(@PathVariable Long id) {
        return caseManagementService.getUserDetails(id);
    }

    @GetMapping("/Data/{id}")
    public ResponseEntity<?> getHearingData(@PathVariable Long id) {
        try {
            CaseHearingEntity getData = caseManagementService.getUserDetails(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Hearing not found");
        }
    }

    @PutMapping("/lev/{id}")
    public CaseHearingEntity updateData(
            @PathVariable Long id,
            @RequestBody CaseHearingEntity data) {

        return caseManagementService.updateDatabase(id, data);
    }

    @DeleteMapping("/mep/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            CaseHearingEntity getData = caseManagementService.getDelete(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Hearing not found");
        }
    }

    @PatchMapping("/patchfav/{id}")
    public CaseHearingEntity patchUserdata(
            @PathVariable Long id,
            @RequestBody CaseHearingEntity data) {

        return caseManagementService.patchUser(id, data);
    }
}