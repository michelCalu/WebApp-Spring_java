package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Citizen extends User {

    private long id;

    public Citizen() {
    }

    public Citizen(long id, String firstName, String lastName, Address address, String mail, String phone,
	    String nationalRegisterNb, LocalDate birthdate) {
	super(firstName, lastName, address, mail, phone, nationalRegisterNb, birthdate);
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Citizen))
	    return false;
	if (!super.equals(o))
	    return false;
	Citizen that = (Citizen) o;
	return id == that.id;
    }

    @Override
    public int hashCode() {
	return Objects.hash(super.hashCode(), id);
    }

    public long getId() {
	return id;
    }
}