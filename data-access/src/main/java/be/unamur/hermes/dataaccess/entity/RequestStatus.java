package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class RequestStatus {

    private long id;
    private String name;
    private String comment;

    public RequestStatus() {
    }

    public RequestStatus(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestStatus)) return false;
        RequestStatus that = (RequestStatus) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RequestStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
