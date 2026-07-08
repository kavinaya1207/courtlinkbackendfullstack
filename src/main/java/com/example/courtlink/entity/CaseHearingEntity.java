package com.example.courtlink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "case_hearings")
public class CaseHearingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerEmail;

    @NotBlank(message = "Case number cannot be blank")
    @Size(min = 3, max = 50, message = "Case number must be between 3 and 50 characters")
    private String caseNumber;

    @NotBlank(message = "Hearing date cannot be blank")
    private String hearingDate;

    @NotBlank(message = "Judge name cannot be blank")
    private String judgeName;

    @NotBlank(message = "Court room cannot be blank")
    private String courtRoom;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Size(max = 255, message = "Remarks cannot exceed 255 characters")
    private String remarks;

    public CaseHearingEntity() {
    }

    public CaseHearingEntity(Long id, String caseNumber, String hearingDate, String judgeName, String courtRoom, String status, String remarks) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.hearingDate = hearingDate;
        this.judgeName = judgeName;
        this.courtRoom = courtRoom;
        this.status = status;
        this.remarks = remarks;
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

    public String getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(String hearingDate) {
        this.hearingDate = hearingDate;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public String getCourtRoom() {
        return courtRoom;
    }

    public void setCourtRoom(String courtRoom) {
        this.courtRoom = courtRoom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
