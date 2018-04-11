package be.unamur.hermes.dataaccess.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;

    @Autowired
    public EmployeeRepositoryImpl(final JdbcTemplate jdbcTemplate, final AddressRepository addressRepository) {
	this.jdbcTemplate = jdbcTemplate;
	this.addressRepository = addressRepository;
    }

    @Override
    public Employee findByName(String firstname, String lastname) {
	    return jdbcTemplate.queryForObject(queryByName, new Object[] { firstname, lastname }, this::buildEmployee);
    }

    @Override
    public Employee findById(long employeeId) {
    	if (employeeId == 0)
	        return null;
	    return jdbcTemplate.queryForObject(queryById, new Object[] { employeeId }, this::buildEmployee);
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(queryAll, this::buildEmployee);
    }

    @Override
    public void create(Employee employee) {
	long addressID = addressRepository.create(employee.getAddress());
	Object[] values = { employee.getFirstName(), employee.getLastName(), addressID, employee.getMail(),
		employee.getPhone(), employee.getNationalRegisterNb(), employee.getBirthdate(),
		employee.getAccountNumber(), employee.getArrivalDate(), employee.getGender(), employee.getCivilStatus(),
		employee.getDependentChildren(), employee.getDependentPeople() };

	int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.CHAR, Types.VARCHAR, Types.INTEGER,
		Types.INTEGER };
	jdbcTemplate.update(createNew, values, types);
    }


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


    // Other methods
    private Employee buildEmployee(ResultSet rs, int rowNum) throws SQLException{
        return new Employee(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                addressRepository.findById(rs.getLong(4)),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getTimestamp(10).toLocalDateTime(),
                rs.getString(11).charAt(0),
                rs.getString(12),
                rs.getInt(13),
                rs.getInt(14)
                );
    }
}