package be.unamur.hermes.dataaccess.entity;

public class Inhabitant extends People {

    private long id;
    private boolean activated;

    public Inhabitant(long peopleID, String firstname, String lastname, long inhabitantID, boolean activated) {
        super(peopleID, firstname, lastname);
        this.id = inhabitantID;
        this.activated = activated;
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
