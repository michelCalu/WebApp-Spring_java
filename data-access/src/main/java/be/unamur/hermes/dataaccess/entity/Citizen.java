package be.unamur.hermes.dataaccess.entity;

import java.sql.ResultSet;
import java.util.Objects;

public class Citizen extends User {

    private long id;
    private boolean activated;

    public Citizen(){};

    public Citizen(
            long id,
            String firstName,
            String lastName,
            Address address,
            String mail,
            String phone,
            String nationalRegisterNb,
            String birthdate,
            boolean activated
    ){
        super(firstName, lastName, address, mail, phone, nationalRegisterNb, birthdate);
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Citizen)) return false;
        if (!super.equals(o)) return false;
        Citizen that = (Citizen) o;
        return id == that.id &&
                activated == that.activated;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, activated);
    }

    public long getId() {
        return id;
    }

    public boolean isActivated() {
        return activated;
    }
}
