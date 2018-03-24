package be.unamur.hermes.dataaccess.entity;

public class PeopleBis {

    private String firstName;
    private String lastName;

    public PeopleBis(){
        //Dummy constructor
    }

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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        People other = (People) obj;
        if (firstName != other.getFirstname() || lastName != other.getLastname())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "People : {\"firstName\":\""+firstName+",\"lastName\":\""+lastName+"\"}";
    }
}
