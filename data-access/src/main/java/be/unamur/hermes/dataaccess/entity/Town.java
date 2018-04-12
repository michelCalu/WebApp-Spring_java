package be.unamur.hermes.dataaccess.entity;

import java.util.List;
import java.util.Objects;

public class Town {

    private long id;
    private String name;
    private List<Employee> employees;
    private List<Department> departments;

    public Town(){};

    public Town(long id,
                String name,
                List<Employee> employees,
                List<Department> departments){
        this.id = id;
        this.name = name;
        this.employees = employees;
        this.departments = departments;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
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
        if (!(o instanceof Town)) return false;
        Town town = (Town) o;
        return id == town.id &&
                Objects.equals(name, town.name) &&
                Objects.equals(employees, town.employees) &&
                Objects.equals(departments, town.departments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, employees, departments);
    }

    @Override
    public String toString() {
        return "Town{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", departments=" + departments +
                '}';
    }
}
