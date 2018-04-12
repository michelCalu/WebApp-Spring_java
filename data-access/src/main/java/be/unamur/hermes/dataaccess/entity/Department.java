package be.unamur.hermes.dataaccess.entity;

import java.util.List;
import java.util.Objects;

public class Department {

    private long id;
    private Employee headOfDepartment;
    private List<Employee> employees;

    //TODO add concerns and skills (compétences (flemme de regarder une bonne traduction sur le net mais j'ai le temps pour écrire ce message))
    // ==> Questions on met ces classes ici ou dans Business ou Common ?
    //     Perso : ce sont des entitées, les préoccupation et compétences peuvent changer ==> à ajouter en DB
    //private List<Concern> concerns;

    public Department(){};

    public Department(long id,
                      Employee headOfDepartment,
                      List<Employee> employees){
        this.id = id;
        this.headOfDepartment = headOfDepartment;
        this.employees = employees;
    }

    public long getId(){
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(headOfDepartment, that.headOfDepartment) &&
                Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, headOfDepartment, employees);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", headOfDepartment=" + headOfDepartment +
                ", employees=" + employees +
                '}';
    }

}
