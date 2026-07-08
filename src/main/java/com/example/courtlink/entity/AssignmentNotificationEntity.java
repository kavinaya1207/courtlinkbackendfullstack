
package com.example.courtlink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "assignment_notifications")
public class AssignmentNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerEmail;

    @NotBlank(message = "Case name cannot be blank")
    @Size(min = 3, max = 100, message = "Case name must be between 3 and 100 characters")
    private String caseName;

    @NotBlank(message = "File name cannot be blank")
    private String fileName;

    @NotBlank(message = "File type cannot be blank")
    private String fileType;

    @NotBlank(message = "Storage URL cannot be blank")
    private String storageUrl;

    @NotBlank(message = "Upload date cannot be blank")
    private String uploadDate;

    @Min(value = 1, message = "Legal Case ID must be a positive number")
    private long legalCaseId;

    public AssignmentNotificationEntity() {
    }

    public AssignmentNotificationEntity(Long id, String caseName, String fileName, String fileType, String storageUrl, String uploadDate, long legalCaseId) {
        this.id = id;
        this.caseName = caseName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.storageUrl = storageUrl;
        this.uploadDate = uploadDate;
        this.legalCaseId = legalCaseId;
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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public long getLegalCaseId() {
        return legalCaseId;
    }

    public void setLegalCaseId(long legalCaseId) {
        this.legalCaseId = legalCaseId;
    }
}
