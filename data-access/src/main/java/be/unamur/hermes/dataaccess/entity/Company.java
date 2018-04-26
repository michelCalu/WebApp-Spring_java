package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class Company {

    private String companyNb;
    private String vatNb;
    private Address address;
    private String judicialForm;
    private Citizen companyOwner;

    public Company() {
    }

    public Company(String companyNb, String vatNb, Address address, String judicialForm, Citizen companyOwner) {
        this.companyNb = companyNb;
        this.vatNb = vatNb;
        this.address = address;
        this.judicialForm = judicialForm;
        this.companyOwner = companyOwner;
    }

    public String getCompanyNb() {
        return companyNb;
    }

    public void setCompanyNb(String companyNb) {
        this.companyNb = companyNb;
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

    public String getJudicialForm() {
        return judicialForm;
    }

    public void setJudicialForm(String judicialForm) {
        this.judicialForm = judicialForm;
    }

    public Citizen getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(Citizen companyOwner) {
        this.companyOwner = companyOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(companyNb, company.companyNb) &&
                Objects.equals(vatNb, company.vatNb) &&
                Objects.equals(address, company.address) &&
                Objects.equals(judicialForm, company.judicialForm) &&
                Objects.equals(companyOwner, company.companyOwner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(companyNb, vatNb, address, judicialForm, companyOwner);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyNb='" + companyNb + '\'' +
                ", vatNb='" + vatNb + '\'' +
                ", address=" + address +
                ", judicialForm='" + judicialForm + '\'' +
                ", companyOwner=" + companyOwner +
                '}';
    }
}
