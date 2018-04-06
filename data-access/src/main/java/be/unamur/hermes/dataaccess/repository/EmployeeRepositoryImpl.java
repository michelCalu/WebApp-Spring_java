package be.unamur.hermes.dataaccess.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Employee;

import java.sql.Types;
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

    private static final String createNew = //
        "INSERT INTO t_employees (" +
                "firstName, lastName, address, " +
                "mail, phone, nationalRegistreNb, " +
                "birthdate, accountNumber, arrivalDate, " +
                "gender, civilStatus, dependentChildren, dependentPeople) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final BeanPropertyRowMapper<Employee> employeeMapper = new BeanPropertyRowMapper<>(Employee.class);

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;

    @Autowired
    public EmployeeRepositoryImpl(final JdbcTemplate jdbcTemplate, final AddressRepository addressRepository) {
	    this.jdbcTemplate = jdbcTemplate;
	    this.addressRepository = addressRepository;
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
        return jdbcTemplate.query(queryAll, employeeMapper);
    }

    @Override
    public void create(Employee employee) {
        long addressID = addressRepository.create(employee.getAddress());
        Object[] values = {
            employee.getFirstName(),
            employee.getLastName(),
            addressID,
            employee.getMail(),
            employee.getPhone(),
            employee.getNationalRegistreNb(),
            employee.getBirthdate(),
            employee.getAccountNumber(),
            employee.getArrivalDate(),
            employee.getGender(),
            employee.getCivilStatus(),
            employee.getDependentChildren(),
            employee.getDependentPeople()
        };

        int[] types = {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.DATE,
                Types.VARCHAR,
                Types.TIMESTAMP,
                Types.CHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER};
        jdbcTemplate.update(createNew, values, types);
    }
}