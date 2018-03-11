package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee findByName(String firstname, String lastname) {
        return jdbcTemplate.queryForObject(
                "SELECT p.peopleID, p.firstname, p.lastname, e.employeeID FROM " +
                        "t_employees e, t_people p WHERE " +
                        "e.peopleID = p.peopleID AND p.firstname = ? AND p.lastname = ?",
                new Object[]{firstname, lastname},
                (rs, rowNum) -> new Employee(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4))
        );
    }

    @Override
    public void create(String firstname, String lastname) {
        long peopleId = jdbcTemplate.update(
                "INSERT INTO t_people (firstname, lastname) VALUES (?, ?)",
                firstname,
                lastname
        );
        jdbcTemplate.update(
                "INSERT INTO t_employees (peopleID) VALUES (?)",
                peopleId
        );
    }
}
