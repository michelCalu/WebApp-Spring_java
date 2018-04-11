package be.unamur.hermes.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Request {

    private Long id;
    private Long typeId;
    private int status;

    // only used for database retrieval
    private Long citizenId;
    private Long employeeId;

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

    public int getStatus() {
	return status;
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
