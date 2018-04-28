package be.unamur.hermes.dataaccess.entity;

public class Municipality {

    private String name;
    private Citizen mayor;
    private Address address;
    private String email;
    private String phoneNumber;
    private String parameters;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Citizen getMayor() {
	return mayor;
    }

    public void setMayor(Citizen mayor) {
	this.mayor = mayor;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
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

    public void setPhoneNumber(String phone) {
	this.phoneNumber = phone;
    }

    public String getParameters() {
	return parameters;
    }

    public void setParameters(String parameters) {
	this.parameters = parameters;
    }
}
