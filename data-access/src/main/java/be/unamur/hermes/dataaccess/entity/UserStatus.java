package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class UserStatus {

    private long id;
    private String value;

    public UserStatus() {
    }

    public UserStatus(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStatus)) return false;
        UserStatus that = (UserStatus) o;
        return id == that.id &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
