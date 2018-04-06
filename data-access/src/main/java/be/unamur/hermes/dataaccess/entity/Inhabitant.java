package be.unamur.hermes.dataaccess.entity;

import java.util.Date;
import java.util.Objects;

public class Inhabitant extends User {

    private long id;
    private boolean activated;

    public Inhabitant(
            String firstname,
            String lastname,
            Address address,
            String mail,
            String phone,
            String nationalRegistreNb,
            Date birthdate,
            long id,
            boolean activated) {
        super(firstname, lastname, address, mail, phone, nationalRegistreNb, birthdate);
        this.id = id;
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inhabitant)) return false;
        if (!super.equals(o)) return false;
        Inhabitant that = (Inhabitant) o;
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

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
