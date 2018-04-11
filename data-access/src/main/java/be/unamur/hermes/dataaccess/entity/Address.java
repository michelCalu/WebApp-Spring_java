package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class Address {

    private long id;
    private String country;
    private String state;
    private int zipCode;
    private String street;
    private int streetNb;

    public Address(){};

    public Address(long id,
                   String country,
                   String state,
                   int zipCode,
                   String street,
                   int streetNb) {
        this.id = id;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
        this.street = street;
        this.streetNb = streetNb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id == address.id &&
                streetNb == address.streetNb &&
                Objects.equals(country, address.country) &&
                Objects.equals(state, address.state) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, country, state, street, streetNb);
    }

    @Override
    public String toString() {
        return street + ", " + streetNb + " " + zipCode + " " + state + ", " + country;
    }


    //---------------------- getters and setters -------------------------//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNb() {
        return streetNb;
    }

    public void setStreetNb(int streetNb) {
        this.streetNb = streetNb;
    }
}
