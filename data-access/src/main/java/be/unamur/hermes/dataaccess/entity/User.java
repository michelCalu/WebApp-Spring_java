package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public abstract class User {

    private String firstName;
    private String lastName;
    private Address address;
    private String mail;
    private String phone;
    private String nationalRegistreNb;
    private String birthdate;

    public User(){};

    public User(
            String firstName,
            String lastName,
            Address address,
            String mail,
            String phone,
            String nationalRegistreNb,
            String birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.nationalRegistreNb = nationalRegistreNb;
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(address, user.address) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(nationalRegistreNb, user.nationalRegistreNb) &&
                Objects.equals(birthdate, user.birthdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, address, mail, phone, nationalRegistreNb, birthdate);
    }


    // ------------------------- Getters and Setters ------------------------- //

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalRegistreNb() {
        return nationalRegistreNb;
    }

    public void setNationalRegistreNb(String nationalRegistreNb) {
        this.nationalRegistreNb = nationalRegistreNb;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
