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

import com.example.courtlink.entity.LegalClientEntity;
import com.example.courtlink.service.LegalClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/api")
public class LegalClientController {

    @Autowired
    LegalClientService legalClientService;

    @PostMapping("/client")
    public LegalClientEntity saveData(@Valid @RequestBody LegalClientEntity data) {
        return legalClientService.saveClient(data);
    }

    @GetMapping("/cli")
    public List<LegalClientEntity> getData() {
        return legalClientService.getAllClients();
    }

    @GetMapping("/ent/{id}")
    public LegalClientEntity getClient(@PathVariable Long id) {
        return legalClientService.getClientById(id);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<?> getClientData(@PathVariable Long id) {
        try {
            LegalClientEntity getData = legalClientService.getClientById(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client not found");
        }
    }

    @PutMapping("/client/{id}")
    public LegalClientEntity updateData(
            @PathVariable Long id,
            @RequestBody LegalClientEntity data) {

        return legalClientService.updateClient(id, data);
    }

    @DeleteMapping("/lie/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            LegalClientEntity getData = legalClientService.deleteClient(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client not found");
        }
    }
    @PatchMapping("/patchfav/{id}")
public LegalClientEntity patchUserdata(
        @PathVariable Long id,
        @RequestBody LegalClientEntity data) {

    return legalClientService.patchUser(id, data);
}
}