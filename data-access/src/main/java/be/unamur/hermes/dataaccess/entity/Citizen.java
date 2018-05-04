package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Citizen extends User {

    public Citizen() {
    }

    public Citizen(long id, String firstName, String lastName, Address address, String mail, String phone,
	    String nationalRegisterNb, LocalDate birthdate) {
	super(firstName, lastName, address, mail, phone, nationalRegisterNb, birthdate);
	setId(id);
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

	@Override
	public String toString() {
		return "Citizen{} \n User : " + super.toString();
	}
}