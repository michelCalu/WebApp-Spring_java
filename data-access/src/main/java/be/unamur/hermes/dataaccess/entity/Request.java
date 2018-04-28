package be.unamur.hermes.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Request {

    private Long id;

    private String type;
    private String userRef;
    private String systemRef;
    private String municipalityRef;

    // only used for database retrieval
    private Long citizenId;
    private Long companyId;
    private Long employeeId;
    private Long typeId;

    // derived information
    private Employee assignee;
    private Citizen citizen;
    private Company company;
    private String status;

    Request() {
	// no-op
    }

    public Request(Long id, Long typeId) {
	super();
	this.id = id;
	this.typeId = typeId;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public Employee getAssignee() {
	return assignee;
    }

    public void setAssignee(Employee assignee) {
	this.assignee = assignee;
    }

    public Citizen getCitizen() {
	return citizen;
    }

    public void setCitizen(Citizen citizen) {
	this.citizen = citizen;
    }

    public Company getCompany() {
	return company;
    }

    public void setCompany(Company company) {
	this.company = company;
    }

    public Long getId() {
	return id;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @JsonIgnore
    public Long getTypeId() {
	return typeId;
    }

    @JsonIgnore
    public Long getCitizenId() {
	return citizenId;
    }

    public void setCitizenId(Long citizenId) {
	this.citizenId = citizenId;
    }

    @JsonIgnore
    public Long getEmployeeId() {
	return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
	this.employeeId = employeeId;
    }

    @JsonIgnore
    public Long getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Long companyId) {
	this.companyId = companyId;
    }

    public String getUserRef() {
	return userRef;
    }

    public void setUserRef(String userRef) {
	this.userRef = userRef;
    }

    public String getSystemRef() {
	return systemRef;
    }

    public void setSystemRef(String systemRef) {
	this.systemRef = systemRef;
    }

    public String getMunicipalityRef() {
	return municipalityRef;
    }

    public void setMunicipalityRef(String municipalityRef) {
	this.municipalityRef = municipalityRef;
    }

    @Override
    public String toString() {
	return "Request [id=" + id + ", typeId=" + typeId + ", status=" + status + ", citizenId=" + citizenId
		+ ", employeeId=" + employeeId + ", assignee=" + assignee + ", citizen=" + citizen + "]";
    }
}
