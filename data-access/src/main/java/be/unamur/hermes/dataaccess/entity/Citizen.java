package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Citizen extends User {

	private Municipality municipality;

    public Citizen() {
    }

    public Citizen(long id, String firstName, String lastName, Address address, String mail, String phone,
	    String nationalRegisterNb, LocalDate birthdate, Municipality municipality) {
	super(firstName, lastName, address, mail, phone, nationalRegisterNb, birthdate);
	setId(id);
	this.municipality = municipality;
    }

	public Municipality getMunicipality() {
		return municipality;
	}

	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	@Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Citizen))
	    return false;
	if (!super.equals(o))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	return Objects.hash(super.hashCode());
    }
}