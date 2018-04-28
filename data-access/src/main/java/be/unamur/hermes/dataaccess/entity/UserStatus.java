package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class UserStatus {

    private long id;
    private String code;

    public UserStatus() {
    }

    public UserStatus(long id, String code) {
        this.id = id;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStatus)) return false;
        UserStatus that = (UserStatus) o;
        return id == that.id &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
