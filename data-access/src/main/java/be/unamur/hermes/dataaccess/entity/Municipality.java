package be.unamur.hermes.dataaccess.entity;

import java.util.List;
import java.util.Objects;

public class Municipality {

    private long id;
    private String name;
    private List<Department> departments;

    public Municipality(){};

    public Municipality(long id,
                        String name,
                        List<Department> departments){
        this.id = id;
        this.name = name;
        this.departments = departments;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Municipality)) return false;
        Municipality municipality = (Municipality) o;
        return id == municipality.id &&
                Objects.equals(name, municipality.name) &&
                Objects.equals(departments, municipality.departments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, departments);
    }

    @Override
    public String toString() {
        return "Municipality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departments=" + departments +
                '}';
    }
}
