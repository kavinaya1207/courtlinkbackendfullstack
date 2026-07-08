package com.example.courtlink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.courtlink.entity.LegalCaseEntity;
import com.example.courtlink.repository.LegalCaseRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class LegalCaseService {

    @Autowired
    private LegalCaseRepository legalCaseRepository;

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
    public LegalCaseEntity saveData(LegalCaseEntity data) {
        data.setOwnerEmail(getCurrentUserEmail());
        return legalCaseRepository.save(data);
    }

    // Get All
    public List<LegalCaseEntity> getAllData() {
        if (isAdmin()) {
            return legalCaseRepository.findAll();
        } else {
            return legalCaseRepository.findByOwnerEmail(getCurrentUserEmail());
        }
    }

    // Get By Id
    public LegalCaseEntity getDataById(Long id) {
        return legalCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Case Not Found with id: " + id));
    }

    public LegalCaseEntity getUserDetails(Long id) {
        LegalCaseEntity caseData = legalCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Case Not Found"));
        
        if (!isAdmin() && !caseData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return caseData;
    }

    public LegalCaseEntity updateDatabase(Long id, LegalCaseEntity data) {

        LegalCaseEntity viewData = legalCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Case Not Found to Update"));
                        
        if (!isAdmin() && !viewData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        viewData.setCaseNumber(data.getCaseNumber());
        viewData.setTitle(data.getTitle());
        viewData.setDescription(data.getDescription());
        viewData.setCourtName(data.getCourtName());
        viewData.setFilingDate(data.getFilingDate());
        viewData.setCaseStatus(data.getCaseStatus());

        return legalCaseRepository.save(viewData);
    }

    public LegalCaseEntity getDelete(Long id) {

        LegalCaseEntity caseData = legalCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Case Not Found to Delete"));
                        
        if (!isAdmin() && !caseData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        legalCaseRepository.delete(caseData);

        return caseData;
    }

    
    public LegalCaseEntity patchUser(Long id, LegalCaseEntity data) {

        LegalCaseEntity legalCase = legalCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Case Not Found"));
                        
        if (!isAdmin() && !legalCase.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        if (data.getCaseNumber() != null)
            legalCase.setCaseNumber(data.getCaseNumber());

        if (data.getTitle() != null)
            legalCase.setTitle(data.getTitle());

        if (data.getDescription() != null)
            legalCase.setDescription(data.getDescription());

        if (data.getCourtName() != null)
            legalCase.setCourtName(data.getCourtName());

        if (data.getFilingDate() != null)
            legalCase.setFilingDate(data.getFilingDate());

        if (data.getCaseStatus() != null)
            legalCase.setCaseStatus(data.getCaseStatus());

        return legalCaseRepository.save(legalCase);
    }
}