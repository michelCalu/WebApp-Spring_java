package be.unamur.hermes.dataaccess.entity;

import be.unamur.hermes.common.enums.UserStatus;

import java.util.Objects;

public class Company {

    private String companyNb;
    private String vatNb;
    private Address address;
    private String legalForm;
    private String contactPerson;
    private String companyName;
    private UserStatus status;

    public Company() {
    }

    public Company(String companyNb, String vatNb, Address address, String legalForm, String contactPerson, String companyName, UserStatus status) {
        this.companyNb = companyNb;
        this.vatNb = vatNb;
        this.address = address;
        this.legalForm = legalForm;
        this.contactPerson = contactPerson;
        this.companyName = companyName;
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getCompanyNb() {
	return companyNb;
    }

    public void setCompanyNb(String companyNb) {
	this.companyNb = companyNb;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setcompanyname(String companyName) {
        this.companyName = companyName;
    }

    public String getVatNb() {
	return vatNb;
    }

    public void setVatNb(String vatNb) {
	this.vatNb = vatNb;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public String getLegalForm() {
	return legalForm;
    }

    public void setLegalForm(String legalForm) {
	this.legalForm = legalForm;
    }

    public String getContactPerson() {
	return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyNb, company.companyNb) &&
                Objects.equals(vatNb, company.vatNb) &&
                Objects.equals(address, company.address) &&
                Objects.equals(legalForm, company.legalForm) &&
                Objects.equals(contactPerson, company.contactPerson) &&
                Objects.equals(companyName, company.companyName) &&
                status == company.status;
    }

    @Override
    public int hashCode() {
	return Objects.hash(companyNb, vatNb, address, legalForm);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyNb='" + companyNb + '\'' +
                ", vatNb='" + vatNb + '\'' +
                ", address=" + address +
                ", legalForm='" + legalForm + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", companyName='" + companyName + '\'' +
                ", status=" + status +
                '}';
    }
}
