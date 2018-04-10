package be.unamur.hermes.dataaccess.entity;

public class Claim {

    // database info
    private final Long id;
    private final Long typeId;
    private int status;

    // derived information
    private Employee assignee;
    private Citizen citizen;

    public Claim(Long id, Long typeId) {
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
}
