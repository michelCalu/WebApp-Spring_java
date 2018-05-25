package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RequestTypeRepositoryImpl implements RequestTypeRepository{

    private JdbcTemplate jdbcTemplate;

    public RequestTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RequestType findById(Long id) {
        return jdbcTemplate.queryForObject(queryById, new Object[] { id }, this::build);
    }

    @Override
    public RequestType findByDescription(String description) {
        return jdbcTemplate.queryForObject(queryByDescription, new Object[] { description }, this::build);
    }

    @Override
    public List<RequestType> findByDepartmentId(Long departmentId) {
        return buildRequestType(jdbcTemplate.queryForList(queryByDepartmentID, departmentId));
    }

    @Override
    public List<RequestType> findByMunicipalityId(Long municipalityId) {
        return buildRequestType(jdbcTemplate.queryForList(queryByMunicipalityID, municipalityId));
    }

    private RequestType build(ResultSet rs, int row) throws SQLException {
        Long id = rs.getLong(1);
        String description = rs.getString(2);
        return new RequestType(id, description);
    }

    private List<RequestType> buildRequestType(List<Map<String, Object>> requestTypesAsRows) {
        List<RequestType> requestTypes = new ArrayList<>();
        for(Map<String, Object> requestTypeRow: requestTypesAsRows){
            String description = (String) requestTypeRow.get("description");
            Integer requestTypeID = (Integer) requestTypeRow.get("requestTypeID");
            requestTypes.add(new RequestType(Integer.toUnsignedLong(requestTypeID),description));
        }
        return requestTypes;
    }

    private static final String selectRequestTypeClause = //
        "SELECT * FROM t_request_types rt ";

    private static final String queryById = //
        selectRequestTypeClause + "WHERE rt.requestTypeID=?";

    private static final String queryByDescription = //
        selectRequestTypeClause + "WHERE rt.description=?" ;

    private static final String queryByDepartmentID = //
        "SELECT rt.* FROM t_request_types rt JOIN " +
            "t_departments_request_types drt ON rt.requestTypeID=drt.requestTypeID WHERE " +
            "drt.departmentID=?";

    private static final String queryByMunicipalityID = //
        "SELECT rt.* FROM ((t_request_types rt JOIN " +
            "t_departments_request_types drt ON rt.requestTypeID=drt.requestTypeID) JOIN " +
            "t_departments d ON drt.departmentID=d.departmentID) WHERE" +
            " d.municipalityID=?";
}
