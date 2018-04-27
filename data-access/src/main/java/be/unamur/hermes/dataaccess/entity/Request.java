package be.unamur.hermes.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import be.unamur.hermes.common.enums.ClaimStatus;

public class Request {

    private Long id;
    private int status;
    private String type;

    // only used for database retrieval
    private Long citizenId;
    private Long employeeId;
    private Long typeId;

    // derived information
    private Employee assignee;
    private Citizen citizen;

    Request() {
	// no-op
    }

    public Request(Long id, Long typeId) {
	super();
	this.id = id;
	this.typeId = typeId;
    }

    @JsonIgnore
    public int getStatusId() {
	return status;
    }

    public String getStatus() {
	return ClaimStatus.values()[status].name();
    }

    public void setStatus(int status) {
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

    @Override
    public String toString() {
	return "Request [id=" + id + ", typeId=" + typeId + ", status=" + status + ", citizenId=" + citizenId
		+ ", employeeId=" + employeeId + ", assignee=" + assignee + ", citizen=" + citizen + "]";
    }
}
