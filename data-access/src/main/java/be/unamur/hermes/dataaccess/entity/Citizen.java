package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class Citizen extends User {

    private long id;
    private boolean activated;

    public Citizen(){};

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
