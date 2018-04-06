package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Citizen extends User {

    private long id;
    private boolean activated;

    public Citizen(){};

    public Citizen(
            Address address,
            String birthdate,
            long id,
            String firstname,
            String lastname,
            String mail,
            String nationalRegistreNb,
            String phone
    ) {
        super(firstname, lastname, address, mail, phone, nationalRegistreNb, birthdate);
        this.activated = false;
    }

    public Citizen(
            String firstname,
            String lastname,
            Address address,
            String mail,
            String phone,
            String nationalRegistreNb,
            String birthdate,
            long id,
            boolean activated) {
        super(firstname, lastname, address, mail, phone, nationalRegistreNb, birthdate);
        this.id = id;
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

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
