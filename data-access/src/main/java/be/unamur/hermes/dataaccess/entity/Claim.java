package be.unamur.hermes.dataaccess.entity;

public class Claim {

    // database info
    private final long id;
    private final long typeId;
    private final long employeeId;
    private final long peopleId;

    // private int status;

    // derived information
    private Employee assignee;
    private Citizen citizen;

    public Claim(long id, long typeId, long employeeId, long peopleId) {
	super();
	this.id = id;
	this.typeId = typeId;
	this.employeeId = employeeId;
	this.peopleId = peopleId;
    }

    // public int getStatus() {
    // return status;
    // }
    //
    // public void setStatus(int status) {
    // this.status = status;
    // }

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

    public long getId() {
	return id;
    }

    public long getTypeId() {
	return typeId;
    }

    public long getEmployeeId() {
	return employeeId;
    }

    public long getPeopleId() {
	return peopleId;
    }
}
