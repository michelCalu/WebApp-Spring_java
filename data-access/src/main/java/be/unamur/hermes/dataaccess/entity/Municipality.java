package be.unamur.hermes.dataaccess.entity;

import java.util.List;
import java.util.Objects;

public class Municipality {

    private long id;
    private String name;
    private List<Department> departments;
    private String mayorName;

    public Municipality(){};

    public Municipality(long id,
                        String name,
                        List<Department> departments,
                        String mayorName){
        this.id = id;
        this.name = name;
        this.departments = departments;
        this.mayorName = mayorName;
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

    public String getMayorName() {
        return mayorName;
    }

    public void setMayorName(String mayorName) {
        this.mayorName = mayorName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Municipality)) return false;
        Municipality municipality = (Municipality) o;
        return id == municipality.id &&
                Objects.equals(name, municipality.name) &&
                Objects.equals(departments, municipality.departments) &&
                Objects.equals(mayorName, municipality.mayorName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, departments, mayorName);
    }

    @Override
    public String toString() {
        return "Municipality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departments=" + departments +
                ", mayorName='" + mayorName + '\'' +
                '}';
    }
}
