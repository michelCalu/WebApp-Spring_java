package be.unamur.hermes.dataaccess.entity;

public abstract class People {

    private long id;
    private String firstname;
    private String lastname;

    public long getId() {
        return id;
    }

    public People(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
