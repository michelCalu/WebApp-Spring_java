package be.unamur.hermes.dataaccess.entity;

import java.util.List;
import java.util.Objects;

public class Department {

    private long id;
    private Municipality municipality;
    private Employee headOfDepartment;
    private List<Employee> employees;
    private List<Skill> skills;

    public Department(){};

    public Department(long id,
                      Municipality municipality,
                      Employee headOfDepartment,
                      List<Employee> employees,
                      List<Skill> skills){
        this.id = id;
        this.municipality = municipality;
        this.headOfDepartment = headOfDepartment;
        this.employees = employees;
        this.skills = skills;
    }

    public long getId(){
        return id;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public Employee getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(Employee headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) { this.skills = skills; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(municipality, that.municipality) &&
                Objects.equals(headOfDepartment, that.headOfDepartment) &&
                Objects.equals(employees, that.employees) &&
                Objects.equals(skills, that.skills);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, municipality, headOfDepartment, employees, skills);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", municipality=" + municipality +
                ", headOfDepartment=" + headOfDepartment +
                ", employees=" + employees +
                ", skills=" + skills +
                '}';
    }

}
