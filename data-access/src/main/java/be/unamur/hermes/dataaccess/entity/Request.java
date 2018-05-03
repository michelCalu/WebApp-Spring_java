package be.unamur.hermes.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Request {

    private Long id;

    private String userRef;
    private String systemRef;
    private String municipalityRef;

    // only used for database retrieval
    private Long citizenId;
    private String companyNb;
    private Long employeeId;
    private Long departmentId;
    private Long typeId;
    private Long statusId;
    private List<Long> dataIds;

    // derived information
    private Employee assignee;
    private Citizen citizen;
    private Company company;
    private Department department;
    private RequestType type;
    private RequestStatus status;
    private List<RequestField> data;

    Request() {
	// no-op
    }

    public Request(Long id, Long typeId) {
	super();
	this.id = id;
	this.typeId = typeId;
    }

    public RequestStatus getStatus() {
	return status;
    }

    public void setStatus(RequestStatus status) {
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

    public RequestType getType() {
	return type;
    }

    public void setType(RequestType type) {
	this.type = type;
    }

    public List<RequestField> getData() {
        return data;
    }

    public void addRequestField(RequestField requestField) {
        this.data.add(requestField);
    }

    public void addRequestFields(List<RequestField> requestFields) {
        this.data.addAll(requestFields);
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
    public String getCompanyNb() {
	return companyNb;
    }

    public void setCompanyId(String companyNb) {
	this.companyNb = companyNb;
    }

    @JsonIgnore
    public Long getDepartmentId() {
        return departmentId;
    }

    @JsonIgnore
    public List<Long> getDataIds() {
        return dataIds;
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
