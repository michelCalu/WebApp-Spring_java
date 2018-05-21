package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.unamur.hermes.dataaccess.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository, ApplicationContextAware {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert departmentInserter;
    private final SimpleJdbcInsert departmentRequestTypeInserter;

    private MunicipalityRepository municipalityRepository;
    private final AddressRepository addressRepository;
    private EmployeeRepository employeeRepository;

    // queries
    private static final String findById =//
		"SELECT * FROM department d WHERE d.departmentID = ?";
    private static final String findByMunicipalityId = //
        "SELECT * FROM department d WHERE d.municipalityID = ?";
    private static final String requestTypeByDepartmentID =//
		"SELECT rt.* FROM (t_departments_request_types drt " +
			"JOIN t_request_types rt ON drt.requestTypeID = rt.requestTypeID) " +
			"WHERE drt.departmentID = ?";

    @Autowired
    public DepartmentRepositoryImpl(JdbcTemplate jdbc, AddressRepository addressRepository) {
        super();
        this.jdbc = jdbc;
        this.addressRepository = addressRepository;
        this.departmentInserter = new SimpleJdbcInsert(jdbc.getDataSource()).
				withTableName("t_departments").usingGeneratedKeyColumns("departmentID");
        this.departmentRequestTypeInserter = new SimpleJdbcInsert(jdbc.getDataSource()).
				withTableName("t_departments_request_types");
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

		Map<String, Object> params = new HashMap<>();
		params.put("municipalityID", department.getMunicipality().getId());
		params.put("name", department.getName());
		params.put("headOfDepartmentID", department.getManager().getId());
		Long parentDepartmentID = department.getParentDepartment() == null ? null : department.getParentDepartment().getId();
		params.put("parentDepartmentID", parentDepartmentID);
		Long addressID = department.getAddress() == null ? null : department.getAddress().getId();
		params.put("addressID", addressID);

		Long departmentID = (Long) departmentInserter.executeAndReturnKey(params);
		List<RequestType> managedRequestTypes = department.getManagedRequestTypes();
		for(RequestType requestType: managedRequestTypes) {
			Map<String, Object> departmentRequestTypeParams = new HashMap<>();
			departmentRequestTypeParams.put("departmentID", departmentID);
			departmentRequestTypeParams.put("requestTypeID", requestType.getId());
			departmentRequestTypeInserter.execute(departmentRequestTypeParams);
		}
		return departmentID;
    }

    @Override
    public List<Department> findByMunicipalityId(long munId) {
        return jdbc.query(findByMunicipalityId, new Object[] { munId }, this::build);
    }

    private List<RequestType> getRequestTypeByDepartment(long departmentId) {
    	return jdbc.queryForList(requestTypeByDepartmentID, new Object[] { departmentId }, RequestType.class);
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
	    Long parentDepartmentID = rs.getLong(5);
        if (parentDepartmentID != 0) {
        	Department parentDepartment = findById(parentDepartmentID);
        	result.setParentDepartment(parentDepartment);
		}
        Long addressId = rs.getLong(6);
        if (addressId != 0) {
            Address address = addressRepository.findById(addressId);
            result.setAddress(address);
        }
        List<RequestType> managedRequestTypes = getRequestTypeByDepartment(id);
        result.setManagedRequestTypes(managedRequestTypes);
        return result;
    }
}
