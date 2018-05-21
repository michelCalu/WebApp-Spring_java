package be.unamur.hermes.dataaccess.entity;

import java.util.ArrayList;
import java.util.List;

public class Request {

    private Long id;

    private String userRef;
    private String systemRef;
    private String municipalityRef;

    // derived information
    private Employee assignee;
    private Citizen citizen;
    private Company company;
    private Department department;
    private String typeDescription;
    private RequestStatus status;
    private List<RequestField> data = new ArrayList<>();

    Request() {
	// no-op
    }

    public Request(Long id) {
	super();
	this.id = id;
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

    public Department getDepartment() {
	return department;
    }

    public void setDepartment(Department department) {
	this.department = department;
    }

    public Long getId() {
	return id;
    }

    public String getTypeDescription() {
	return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
	this.typeDescription = typeDescription;
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
        return "Request{" +
                "id=" + id +
                ", userRef='" + userRef + '\'' +
                ", systemRef='" + systemRef + '\'' +
                ", municipalityRef='" + municipalityRef + '\'' +
                ", assignee=" + assignee +
                ", citizen=" + citizen +
                ", company=" + company +
                ", department=" + department +
                ", typeDescription='" + typeDescription + '\'' +
                ", status=" + status +
                ", data=" + data+
                '}';
    }
}
