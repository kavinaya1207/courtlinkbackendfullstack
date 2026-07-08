package com.example.courtlink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class LegalCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerEmail;

    @NotBlank(message = "Case number cannot be blank")
    @Size(min = 3, max = 50, message = "Case number must be between 3 and 50 characters")
    private String caseNumber;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Court name cannot be blank")
    private String courtName;

    @NotBlank(message = "Filing date cannot be blank")
    private String filingDate;

    @NotBlank(message = "Case status cannot be blank")
    private String caseStatus;

    public LegalCaseEntity(String caseNumber, String caseStatus, String courtName, String description, String filingDate, Long id, String title) {
        this.caseNumber = caseNumber;
        this.caseStatus = caseStatus;
        this.courtName = courtName;
        this.description = description;
        this.filingDate = filingDate;
        this.id = id;
        this.title = title;
    }


    public LegalCaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }
}
