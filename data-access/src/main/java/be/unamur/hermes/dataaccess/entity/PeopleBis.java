package be.unamur.hermes.dataaccess.entity;

public class PeopleBis {

    private String firstName;
    private String lastName;

    public PeopleBis(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }
}
