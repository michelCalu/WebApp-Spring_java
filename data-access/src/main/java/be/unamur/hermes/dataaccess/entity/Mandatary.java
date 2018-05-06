package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

import be.unamur.hermes.common.enums.MandataryRole;

public class Mandatary {

    private long id;
    private Citizen citizen;
    private Company company;
    private MandataryRole role;

    public Mandatary() {
    }

    public Mandatary(long id, Citizen citizen, Company company, MandataryRole role) {
	this.id = id;
	this.citizen = citizen;
	this.company = company;
	this.role = role;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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

    public MandataryRole getRole() {
	return role;
    }

    public void setRole(MandataryRole role) {
	this.role = role;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Mandatary))
	    return false;
	Mandatary mandatary = (Mandatary) o;
	return id == mandatary.id && Objects.equals(citizen, mandatary.citizen)
		&& Objects.equals(company, mandatary.company) && Objects.equals(role, mandatary.role);
    }

    @Override
    public int hashCode() {

	return Objects.hash(id, citizen, company, role);
    }

    @Override
    public String toString() {
	return "Mandatary{" + "id=" + id + ", citizen=" + citizen + ", company=" + company + ", role=" + role + '}';
    }
}
