package be.unamur.hermes.dataaccess.entity;

public class Employee extends People{

    private long id;

    public Employee(long peopleID, String firstname, String lastname, long employeeId) {
        super(peopleID, firstname, lastname);
        this.id = employeeId;
    }

    public long getId() {
        return id;
    }
}
