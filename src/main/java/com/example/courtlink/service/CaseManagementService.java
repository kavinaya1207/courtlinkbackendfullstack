package com.example.courtlink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.courtlink.entity.CaseHearingEntity;
import com.example.courtlink.repository.CaseHearingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CaseManagementService {

    @Autowired
    private CaseHearingRepository caseHearingRepository;

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

    public CaseHearingEntity saveData(CaseHearingEntity data) {
        data.setOwnerEmail(getCurrentUserEmail());
        return caseHearingRepository.save(data);
    }

    public List<CaseHearingEntity> getAllData() {
        if (isAdmin()) {
            return (List<CaseHearingEntity>) caseHearingRepository.findAll();
        } else {
            return caseHearingRepository.findByOwnerEmail(getCurrentUserEmail());
        }
    }

    public CaseHearingEntity getDataById(Long id) {
        return caseHearingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Case Hearing Not Found with id: " + id));
    }

    public CaseHearingEntity getUserDetails(Long id) {
        CaseHearingEntity data = caseHearingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Case Hearing Not Found"));
        
        if (!isAdmin() && !data.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return data;
    }

    public CaseHearingEntity updateDatabase(Long id, CaseHearingEntity data) {

        CaseHearingEntity viewData = caseHearingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Case Hearing Not Found to Update"));
                        
        if (!isAdmin() && !viewData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        viewData.setCaseNumber(data.getCaseNumber());
        viewData.setHearingDate(data.getHearingDate());
        viewData.setJudgeName(data.getJudgeName());
        viewData.setCourtRoom(data.getCourtRoom());
        viewData.setStatus(data.getStatus());
        viewData.setRemarks(data.getRemarks());

        return caseHearingRepository.save(viewData);
    }

    public CaseHearingEntity getDelete(Long id) {

        CaseHearingEntity hearingData = caseHearingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Case Hearing Not Found to Delete"));
                        
        if (!isAdmin() && !hearingData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        caseHearingRepository.delete(hearingData);
        return hearingData;
    }
     public CaseHearingEntity patchUser(Long id, CaseHearingEntity data) {

        CaseHearingEntity hearing = caseHearingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Case Hearing Not Found"));
                        
        if (!isAdmin() && !hearing.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        if (data.getCaseNumber() != null)
            hearing.setCaseNumber(data.getCaseNumber());

        if (data.getHearingDate() != null)
            hearing.setHearingDate(data.getHearingDate());

        if (data.getJudgeName() != null)
            hearing.setJudgeName(data.getJudgeName());

        if (data.getCourtRoom() != null)
            hearing.setCourtRoom(data.getCourtRoom());

        if (data.getStatus() != null)
            hearing.setStatus(data.getStatus());

        if (data.getRemarks() != null)
            hearing.setRemarks(data.getRemarks());

        return caseHearingRepository.save(hearing);
    }
}