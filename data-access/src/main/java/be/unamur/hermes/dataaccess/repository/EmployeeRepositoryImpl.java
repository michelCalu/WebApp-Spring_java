package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;
    private final SimpleJdbcInsert inserter;
    private final SimpleJdbcInsert departmentInserter;

    @Autowired
    public EmployeeRepositoryImpl(final JdbcTemplate jdbcTemplate, final AddressRepository addressRepository) {
	this.jdbcTemplate = jdbcTemplate;
	this.addressRepository = addressRepository;
	this.inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_employees")
		.usingGeneratedKeyColumns("employeeID");
	this.departmentInserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
		.withTableName("t_departments_employees");
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
    public long create(Employee employee, long userAccountID) {
	long addressID = addressRepository.create(employee.getAddress());
	Map<String, Object> params = new HashMap<>();
	params.put("firstName", employee.getFirstName());
	params.put("lastName", employee.getLastName());
	params.put("addressID", addressID);
	params.put("mail", employee.getMail());
	params.put("phone", employee.getPhone());
	params.put("nationalRegisterNb", employee.getNationalRegisterNb());
	params.put("birthdate", employee.getBirthdate());
	params.put("accountNumber", employee.getAccountNumber());
	params.put("arrivalDate", employee.getArrivalDate());
	params.put("gender", employee.getGender());
	params.put("civilStatus", employee.getCivilStatus());
	params.put("dependentChildren", employee.getDependentChildren());
	params.put("dependentPeople", employee.getDependentPeople());
	params.put("userAccountID", userAccountID);
	Long employeeId = (Long) inserter.executeAndReturnKey(params);
	if (employee.getDepartmentIds() != null) {
	    employee.getDepartmentIds().forEach(did -> jdbcTemplate.update(createDepartmentIds, did, employeeId));
	}
	return employeeId;
    }

    @Override
    public UserAccount findAccount(String nationalRegistrationNb) {
	return jdbcTemplate.queryForObject(queryAccountByNRN, new Object[] { nationalRegistrationNb },
		this::buildAccount);
    }

    // queries
    private static final String queryById = //
	    "SELECT * FROM t_employees e WHERE e.employeeID = ? ";

    private static final String queryAccountByNRN = //
	    "SELECT e.employeeID, e.userAccountID, e.nationalRegisterNb, ua.roles, ua.userStatus, ua.password FROM t_employees e"
		    + " JOIN t_user_accounts ua ON e.userAccountID = ua.userAccountID WHERE e.nationalRegisterNb = ?";

    private static final String queryByName = //
	    "SELECT * FROM t_employees e WHERE e.firstname = ? AND e.lastname = ?";

    private static final String queryAll = //
	    "SELECT * FROM t_employees";

    private static final String findDepartmentIds = //
	    "SELECT departmentID from t_departments_employees WHERE employeeID = ?";

    private static final String createDepartmentIds = //
	    "INSERT IGNORE INTO t_departments_employees VALUES(?,?)";

    // Other methods
    private Employee buildEmployee(ResultSet rs, int rowNum) throws SQLException {
	Employee result = new Employee(rs.getLong(1), rs.getString(2), rs.getString(3),
		addressRepository.findById(rs.getLong(4)), rs.getString(5), rs.getString(6), rs.getString(7),
		rs.getDate(8).toLocalDate(), rs.getString(9), rs.getDate(10).toLocalDate(), rs.getString(11).charAt(0),
		rs.getString(12), rs.getInt(13), rs.getInt(14));
	List<Long> departementIds = jdbcTemplate.queryForList(findDepartmentIds, Long.class,
		new Object[] { result.getId() });
	result.setDepartmentIds(departementIds);
	return result;
    }

    private UserAccount buildAccount(ResultSet rs, int rowNum) throws SQLException {
	long technicalId = rs.getLong(1);
	long userAccountId = rs.getLong(2);
	String nrn = rs.getString(3);
	String roles = rs.getString(4);
	UserStatus userStatus = UserStatus.getStatus(rs.getString(5));
	String password = rs.getString(6);
	return new UserAccount(userAccountId, technicalId, nrn, UserType.EMPLOYEE, userStatus, password,
		UserAccount.prepareAuthorities(roles));
    }
}