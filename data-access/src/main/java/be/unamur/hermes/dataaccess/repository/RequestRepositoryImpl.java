package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.CreateRequest;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestStatus;
import be.unamur.hermes.dataaccess.entity.RequestType;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private static final String selectRequestClause = //
	    "SELECT req.requestID, req.requestTypeID, req.employeeID, req.citizenID, req.statusId, "
		    + "req.systemRef, req.userRef, req.municipalityRef FROM t_requests req";

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
    private static final String querStatusTypeById = //
	    "SELECT st.statusID, st.statusName FROM t_req_statusses st WHERE st.statusID = ? ";
    private static final String querStatusTypeByName = //
	    "SELECT st.statusID, st.statusName FROM t_req_statusses st WHERE st.statusName = ? ";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;
    private final CitizenRepository citizenRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RequestRepositoryImpl(JdbcTemplate jdbcTemplate, CitizenRepository citizenRepository,
	    EmployeeRepository employeeRepository) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_requests")
		.usingGeneratedKeyColumns("requestID");
	this.citizenRepository = citizenRepository;
	this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Request> findByCitizen(long citizenId) {
	List<Request> requests = jdbcTemplate.query(queryByCitizenId, new Object[] { citizenId },
		new RequestRowMapper());
	requests.stream().forEach(this::fillRequest);
	return requests;
    }

    @Override
    public List<Request> findByCitizen(long citizenId, long requestTypeId) {
	List<Request> requests = jdbcTemplate.query(queryByDepartmentId, new Object[] { citizenId, requestTypeId },
		new RequestRowMapper());
	requests.stream().forEach(this::fillRequest);
	return requests;
    }

    @Override
    public Request findById(long id) {
	Request result = jdbcTemplate.queryForObject(queryById, new Object[] { id }, new RequestRowMapper());
	fillRequest(result);
	return result;
    }

    @Override
    public List<Request> findbyDepartmentId(long departmentId) {
	List<Request> requests = jdbcTemplate.query(queryByCitizenIdAndRequestType, new Object[] { departmentId },
		new RequestRowMapper());
	requests.stream().forEach(this::fillRequest);
	return requests;
    }

    @Override
    public List<Request> findbyAssigneeId(long employeeId) {
	List<Request> requests = jdbcTemplate.query(queryByEmployeeId, new Object[] { employeeId },
		new RequestRowMapper());
	requests.stream().forEach(this::fillRequest);
	return requests;
    }

    @Override
    public long create(CreateRequest newRequest) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("requestTypeID", newRequest.getRequestTypeId());
	parameters.put("citizenID", newRequest.getCitizen());
	RequestStatus newStatus = findRequestStatusByName(RequestRepository.STATUS_NEW);
	parameters.put("status", newStatus.getId());
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
	return jdbcTemplate.queryForObject(querStatusTypeById, new Object[] { id },
		(rs, rowId) -> new RequestStatus(rs.getLong(1), rs.getString(2)));
    }

    private RequestStatus findRequestStatusByName(String name) {
	return jdbcTemplate.queryForObject(querStatusTypeByName, new Object[] { name },
		(rs, rowId) -> new RequestStatus(rs.getLong(1), rs.getString(2)));
    }

    private void fillRequest(Request request) {
	Citizen citizen = citizenRepository.findById(request.getCitizenId());
	RequestType reqType = findRequestTypeById(request.getTypeId());
	request.setCitizen(citizen);
	request.setType(reqType.getDescription());
	if (request.getEmployeeId() > 0) {
	    Employee assignee = employeeRepository.findById(request.getEmployeeId());
	    request.setAssignee(assignee);
	}
    }

    private class RequestRowMapper implements RowMapper<Request> {
	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Request request = new Request(rs.getLong(1), rs.getLong(2));
	    request.setEmployeeId(rs.getLong(3));
	    request.setCitizenId(rs.getLong(4));
	    Long statusId = rs.getLong(5);
	    RequestStatus status = findRequestStatusById(statusId);
	    request.setStatus(status.getName());
	    request.setSystemRef(rs.getString(6));
	    request.setUserRef(rs.getString(7));
	    request.setMunicipalityRef(rs.getString(8));
	    return request;
	}
    }
}
