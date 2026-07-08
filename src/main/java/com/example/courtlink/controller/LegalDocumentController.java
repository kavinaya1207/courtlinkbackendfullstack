package com.example.courtlink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.courtlink.entity.LegalDocumentEntity;
import com.example.courtlink.service.LegalDocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/req/api")
public class LegalDocumentController {

    @Autowired
    LegalDocumentService legalDocumentService;

    @PostMapping("/postdoc")
    public LegalDocumentEntity saveData(@Valid @RequestBody LegalDocumentEntity data) {
        return legalDocumentService.saveCase(data);
    }

    @GetMapping("/tdo")
    public List<LegalDocumentEntity> getData() {
        return legalDocumentService.getAllCases();
    }

    @GetMapping("/getdoc/{id}")
    public LegalDocumentEntity getDocument(@PathVariable Long id) {
        return legalDocumentService.getCaseById(id);
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getDocumentData(@PathVariable Long id) {
        try {
            LegalDocumentEntity getData = legalDocumentService.getCaseById(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Legal Document not found");
        }
    }

    @PutMapping("/putdoc/{id}")
    public LegalDocumentEntity updateData(
            @PathVariable Long id,
            @RequestBody LegalDocumentEntity data) {

        return legalDocumentService.updateCase(id, data);
    }

    @DeleteMapping("/deldoc/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            LegalDocumentEntity getData = legalDocumentService.deleteCase(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Legal Document not found");
        }
    }
    @PatchMapping("/patchfav/{id}")
public LegalDocumentEntity patchUserdata(
        @PathVariable Long id,
        @RequestBody LegalDocumentEntity data) {

    return legalDocumentService.patchUser(id, data);
}
}