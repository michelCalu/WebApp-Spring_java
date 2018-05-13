package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;
import be.unamur.hermes.dataaccess.entity.RequestStatus;
import be.unamur.hermes.dataaccess.entity.RequestType;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private static final String selectRequestClause = //
	    "SELECT * FROM t_requests req";

    // queries
    private static final String queryById = //
	    selectRequestClause + " WHERE req.requestID=?";
    private static final String queryByCitizenId = //
	    selectRequestClause + " WHERE req.citizenID = ?";
    private static final String queryByDepartmentId = //
	    selectRequestClause + " WHERE req.departmentId = ?";
    private static final String queryByEmployeeId = //
	    selectRequestClause + " WHERE req.employeeId = ?";
    private static final String queryByCitizenIdAndRequestType = queryByCitizenId //
	    + " AND req.requestTypeID = ?";
    private static final String queryRequestTypeByDescription = //
	    "SELECT rt.requestTypeID, rt.description FROM t_request_types rt WHERE rt.description = ? ";
    private static final String queryRequestTypeById = //
	    "SELECT rt.requestTypeID, rt.description FROM t_request_types rt WHERE rt.requestTypeID = ? ";
    private static final String queryStatusTypeById = //
	    "SELECT st.statusID, st.statusName FROM t_req_statusses st WHERE st.statusID = ? ";
    private static final String queryStatusTypeByName = //
	    "SELECT st.statusID, st.statusName FROM t_req_statusses st WHERE st.statusName = ? ";
    private static final String updateStatus = //
	    "UPDATE t_requests SET statusID = ? WHERE requestID = ? ";
    private static final String updateAssignee = //
	    "UPDATE t_requests SET employeeID = ? WHERE requestID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;
    private final CitizenRepository citizenRepository;
    private final EmployeeRepository employeeRepository;
    private final RequestFieldRepository requestFieldRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public RequestRepositoryImpl(JdbcTemplate jdbcTemplate, CitizenRepository citizenRepository,
	    EmployeeRepository employeeRepository, RequestFieldRepository requestFieldRepository,
	    DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_requests")
		.usingGeneratedKeyColumns("requestID");
	this.citizenRepository = citizenRepository;
	this.employeeRepository = employeeRepository;
	this.requestFieldRepository = requestFieldRepository;
	this.departmentRepository = departmentRepository;
	this.companyRepository = companyRepository;
    }

    @Override
    public List<Request> findByCitizen(long citizenId) {
	List<Request> requests = jdbcTemplate.query(queryByCitizenId, new Object[] { citizenId }, this::fillRequest);
	return requests;
    }

    @Override
    public List<Request> findByCitizen(long citizenId, long requestTypeId) {
	List<Request> requests = jdbcTemplate.query(queryByCitizenIdAndRequestType,
		new Object[] { citizenId, requestTypeId }, this::fillRequest);
	return requests;
    }

    @Override
    public Request findById(long id) {
	Request result = jdbcTemplate.queryForObject(queryById, new Object[] { id }, this::fillRequest);
	return result;
    }

    @Override
    public List<Request> findbyDepartmentId(long departmentId) {
	List<Request> requests = jdbcTemplate.query(queryByDepartmentId, new Object[] { departmentId },
		this::fillRequest);
	return requests;
    }

    @Override
    public List<Request> findbyAssigneeId(long employeeId) {
	List<Request> requests = jdbcTemplate.query(queryByEmployeeId, new Object[] { employeeId }, this::fillRequest);
	return requests;
    }

    @Override
    public long create(Request newRequest) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("requestTypeID", newRequest.getType().getId());
	parameters.put("citizenID", newRequest.getCitizen().getId());
	parameters.put("companyNb", newRequest.getCompany().getCompanyNb());
	parameters.put("departmentID", newRequest.getDepartment().getId());
	RequestStatus newStatus = findRequestStatusByName(RequestRepository.STATUS_NEW);
	parameters.put("statusID", newStatus.getId());
	parameters.put("systemRef", newRequest.getSystemRef());
	parameters.put("userRef", newRequest.getUserRef());
	parameters.put("municipalityRef", newRequest.getMunicipalityRef());
	return (Long) inserter.executeAndReturnKey(parameters);
    }

    @Override
    public RequestType findRequestTypeByDescription(String description) {
	return jdbcTemplate.queryForObject(queryRequestTypeByDescription, new Object[] { description },
		(rs, rowId) -> new RequestType(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public RequestType findRequestTypeById(long id) {
	return jdbcTemplate.queryForObject(queryRequestTypeById, new Object[] { id },
		(rs, rowId) -> new RequestType(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public RequestStatus findRequestStatusById(long id) {
	return jdbcTemplate.queryForObject(queryStatusTypeById, new Object[] { id },
		(rs, rowId) -> new RequestStatus(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public void updateStatus(Request request) {
	RequestStatus status = request.getStatus();
	jdbcTemplate.update(updateStatus, status.getId(), request.getId());
    }

    @Override
    public void updateAssignee(Request request) {
	jdbcTemplate.update(updateAssignee, request.getAssignee().getId(), request.getId());
    }

    private RequestStatus findRequestStatusByName(String name) {
	return jdbcTemplate.queryForObject(queryStatusTypeByName, new Object[] { name },
		(rs, rowId) -> new RequestStatus(rs.getLong(1), rs.getString(2)));
    }

    private Request fillRequest(ResultSet rs, int rowNum) throws SQLException {
	Request request = new Request(rs.getLong(1));
	RequestType reqType = findRequestTypeById(rs.getLong(2));
	request.setType(reqType);
	Citizen citizen = citizenRepository.findById(rs.getLong(3));
	request.setCitizen(citizen);
	RequestStatus status = findRequestStatusById(rs.getLong(7));
	request.setStatus(status);
	request.setSystemRef(rs.getString(8));
	request.setUserRef(rs.getString(9));
	request.setMunicipalityRef(rs.getString(10));
	Department department = departmentRepository.findById(rs.getLong(6));
	request.setDepartment(department);
	List<RequestField> requestFields = requestFieldRepository.getFields(request.getId());
	request.addRequestFields(requestFields);
	Company company = companyRepository.findByCompanyNb(rs.getString(4));
	request.setCompany(company);
	long employeeId = rs.getLong(5);
	if (employeeId > 0) {
	    Employee assignee = employeeRepository.findById(employeeId);
	    request.setAssignee(assignee);
	}
	return request;
    }
}
