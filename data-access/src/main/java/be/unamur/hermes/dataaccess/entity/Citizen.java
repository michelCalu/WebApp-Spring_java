package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class Citizen extends User {

    private long citizenID;
    private boolean activated;

    public Citizen(){};

    public Citizen(
            String firstName, String lastName, Address address, String mail, String phone,
            String nationalRegisterNb, String birthdate
    ){
        super(firstName, lastName, address, mail, phone, nationalRegisterNb, birthdate);
        this.activated = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Citizen)) return false;
        if (!super.equals(o)) return false;
        Citizen that = (Citizen) o;
        return citizenID == that.citizenID &&
                activated == that.activated;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), citizenID, activated);
    }

    public long getCitizenID() {
        return citizenID;
    }

    public boolean isActivated() {
        return activated;
    }
}
