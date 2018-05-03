package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Municipality;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository, ApplicationContextAware {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    private MunicipalityRepository municipalityRepository;
    private final AddressRepository addressRepository;
    private EmployeeRepository employeeRepository;

    // queries
    private static final String findById = //
	    "SELECT * FROM t_departments WHERE departmentID = ?";
    private static final String findByMunicipalityId = //
	    "SELECT * FROM t_departments WHERE municipalityID = ?";

    @Autowired
    public DepartmentRepositoryImpl(JdbcTemplate jdbc, AddressRepository addressRepository) {
	super();
	this.jdbc = jdbc;
	this.addressRepository = addressRepository;
	this.inserter = new SimpleJdbcInsert(jdbc.getDataSource()).withTableName("t_departments")
		.usingGeneratedKeyColumns("departmentID");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
	// avoiding circular reference
	this.employeeRepository = applicationContext.getBean(EmployeeRepository.class);
	this.municipalityRepository = applicationContext.getBean(MunicipalityRepository.class);
    }

    @Override
    public Department findById(long id) {
	return jdbc.queryForObject(findById, new Object[] { id }, this::build);
    }

    @Override
    public long create(Department department) {
	// TODO
	throw new UnsupportedOperationException("Municipality has changed on other branch");
    }

    @Override
    public List<Department> findByMunicipalityId(long munId) {
	return jdbc.query(findByMunicipalityId, new Object[] { munId }, this::build);
    }

    private Department build(ResultSet rs, int row) throws SQLException {
	Department result = new Department();
	long id = rs.getLong(1);
	result.setId(id);
	long municipalityID = rs.getLong(2);
	Municipality munip = municipalityRepository.findById(municipalityID);
	result.setMunicipality(munip);
	String name = rs.getString(3);
	result.setName(name);
	Long headOfDepartmentID = rs.getLong(4);
	if (headOfDepartmentID != 0) {
	    Employee headOfDepartment = employeeRepository.findById(headOfDepartmentID);
	    result.setManager(headOfDepartment);
	}
	// TODO parentDepartmentID
	Long addressId = rs.getLong(6);
	if (addressId != 0) {
	    Address address = addressRepository.findById(addressId);
	    result.setAddress(address);
	}
	return result;
    }
}
