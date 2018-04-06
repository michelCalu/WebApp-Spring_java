package be.unamur.hermes.dataaccess.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Employee;

import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    // queries
    private static final String queryById = //
	    "SELECT * FROM t_employees e WHERE e.employeeID = ? ";

    private static final String queryByName = //
        "SELECT * FROM t_employees e WHERE e.firstname = ? AND e.lastname = ?";

    private static final String queryAll = //
        "SELECT * FROM t_employees";

    private static final BeanPropertyRowMapper<Employee> employeeMapper = new BeanPropertyRowMapper<>(Employee.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee findByName(String firstname, String lastname) {
	    return jdbcTemplate.queryForObject(queryByName, new Object[] { firstname, lastname }, employeeMapper);
    }

    @Override
    public Employee findById(long employeeId) {
	    return jdbcTemplate.queryForObject(queryById, new Object[] { employeeId }, employeeMapper);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = jdbcTemplate.query(queryAll, employeeMapper);
        return employees;
    }

    @Override
    public void create(String firstname, String lastname) {
	    long peopleId = jdbcTemplate.update("INSERT INTO t_people (firstname, lastname) VALUES (?, ?)", firstname,
		    lastname);
	    jdbcTemplate.update("INSERT INTO t_employees (peopleID) VALUES (?)", peopleId);
    }
}