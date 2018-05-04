package be.unamur.hermes.dataaccess.entity;

public class Municipality {

    private long id;
    private String name;
    private Address address;
    private String email;
    private String phone;
    private String mayorName;

    public Municipality(){};

    public Municipality(
            long id,
            String name,
            Address address,
            String email,
            String phone,
            String mayorName
    ){
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.mayorName = mayorName;
    }

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

    public String getMayorName() {
	return mayorName;
    }

    public void setMayorName(String mayorName) {
	this.mayorName = mayorName;
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

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

}
