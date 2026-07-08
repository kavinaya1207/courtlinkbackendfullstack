package com.example.courtlink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.courtlink.entity.LegalClientEntity;
import com.example.courtlink.repository.LegalClientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class LegalClientService {

    @Autowired
    private LegalClientRepository legalClientRepository;

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
    public LegalClientEntity saveClient(LegalClientEntity clientData) {
        clientData.setOwnerEmail(getCurrentUserEmail());
        return legalClientRepository.save(clientData);
    }

    // Get All
    public List<LegalClientEntity> getAllClients() {
        if (isAdmin()) {
            return (List<LegalClientEntity>) legalClientRepository.findAll();
        } else {
            return legalClientRepository.findByOwnerEmail(getCurrentUserEmail());
        }
    }

    // Get By Id
    public LegalClientEntity getClientById(Long id) {
        LegalClientEntity clientData = legalClientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Client Not Found with id: " + id));
                        
        if (!isAdmin() && !clientData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return clientData;
    }

    // Update (PUT)
    public LegalClientEntity updateClient(Long id, LegalClientEntity clientData) {

        LegalClientEntity viewData = legalClientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Client Not Found to Update"));
                        
        if (!isAdmin() && !viewData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        viewData.setName(clientData.getName());
        viewData.setEmail(clientData.getEmail());
        viewData.setPhone(clientData.getPhone());
        viewData.setAddress(clientData.getAddress());
        viewData.setCaseType(clientData.getCaseType());
        viewData.setStatus(clientData.getStatus());

        return legalClientRepository.save(viewData);
    }

    // Delete
    public LegalClientEntity deleteClient(Long id) {

        LegalClientEntity clientData = legalClientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Client Not Found to Delete"));
                        
        if (!isAdmin() && !clientData.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        legalClientRepository.delete(clientData);

        return clientData;
    }

    public LegalClientEntity patchUser(Long id, LegalClientEntity data) {

        LegalClientEntity client = legalClientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legal Client Not Found"));
                        
        if (!isAdmin() && !client.getOwnerEmail().equals(getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        if (data.getName() != null)
            client.setName(data.getName());

        if (data.getEmail() != null)
            client.setEmail(data.getEmail());

        if (data.getPhone() != null)
            client.setPhone(data.getPhone());

        if (data.getAddress() != null)
            client.setAddress(data.getAddress());

        if (data.getCaseType() != null)
            client.setCaseType(data.getCaseType());

        if (data.getStatus() != null)
            client.setStatus(data.getStatus());

        return legalClientRepository.save(client);
    }
}