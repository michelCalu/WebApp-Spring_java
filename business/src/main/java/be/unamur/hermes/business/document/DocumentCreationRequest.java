package be.unamur.hermes.business.document;

import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Request;

public class DocumentCreationRequest {

    private Department department;
    private Citizen requestor;
    private Employee officer;
    private Request request;

    public Department getDepartment() {
	return department;
    }

    public void setDepartment(Department department) {
	this.department = department;
    }

    public Citizen getRequestor() {
	return requestor;
    }

    public void setRequestor(Citizen requestor) {
	this.requestor = requestor;
    }

    public Employee getOfficer() {
	return officer;
    }

    public void setOfficer(Employee officer) {
	this.officer = officer;
    }

    public Request getRequest() {
	return request;
    }

    public void setRequest(Request request) {
	this.request = request;
    }
}
