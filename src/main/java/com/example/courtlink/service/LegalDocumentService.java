package com.example.courtlink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.courtlink.entity.LegalDocumentEntity;
import com.example.courtlink.repository.LegalDocumentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class LegalDocumentService {

    @Autowired
    private LegalDocumentRepository legalDocumentRepository;

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
    
    public LegalDocumentEntity saveCase(LegalDocumentEntity caseData) {
        caseData.setOwnerEmail(getCurrentUserEmail());
        return legalDocumentRepository.save(caseData);
    }

 
    public List<LegalDocumentEntity> getAllCases() {
        if (isAdmin()) {
            return legalDocumentRepository.findAll();
        } else {
            return legalDocumentRepository.findByOwnerEmail(getCurrentUserEmail());
        }
    }

   
    public LegalDocumentEntity getCaseById(Long id) {
        LegalDocumentEntity document = legalDocumentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Document Not Found with id: " + id));
                        
        if (!isAdmin() && !document.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return document;
    }

  
    public LegalDocumentEntity updateCase(Long id, LegalDocumentEntity caseData) {

        LegalDocumentEntity viewData = legalDocumentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Document Not Found to Update"));
                        
        if (!isAdmin() && !viewData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        viewData.setId(caseData.getId());
        viewData.setFileName(caseData.getFileName());
        viewData.setFileType(caseData.getFileType());
        viewData.setStorageUrl(caseData.getStorageUrl());
        viewData.setUploadDate(caseData.getUploadDate());

        return legalDocumentRepository.save(viewData);
    }

   
    public LegalDocumentEntity deleteCase(Long id) {

        LegalDocumentEntity caseData = legalDocumentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Document Not Found to Delete"));
                        
        if (!isAdmin() && !caseData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        legalDocumentRepository.delete(caseData);

        return caseData;
    }

   
    public LegalDocumentEntity patchUser(Long id, LegalDocumentEntity data) {

        LegalDocumentEntity document = legalDocumentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Document Not Found"));
                        
        if (!isAdmin() && !document.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        if (data.getFileName() != null)
            document.setFileName(data.getFileName());

        if (data.getFileType() != null)
            document.setFileType(data.getFileType());

        if (data.getStorageUrl() != null)
            document.setStorageUrl(data.getStorageUrl());

        if (data.getUploadDate() != null)
            document.setUploadDate(data.getUploadDate());

        return legalDocumentRepository.save(document);
    }
}