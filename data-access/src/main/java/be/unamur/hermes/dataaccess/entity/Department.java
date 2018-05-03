package be.unamur.hermes.dataaccess.entity;

public class Department {

    private long id;
    private String name;
    private Address address;
    private Employee manager;
    private String email;
    private String phoneNumber;
    private Municipality municipality;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public Employee getManager() {
	return manager;
    }

    public void setManager(Employee manager) {
	this.manager = manager;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public Municipality getMunicipality() {
	return municipality;
    }

    public void setMunicipality(Municipality municipality) {
	this.municipality = municipality;
    }
}
