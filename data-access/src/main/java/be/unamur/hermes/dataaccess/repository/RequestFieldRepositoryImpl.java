package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;
import be.unamur.hermes.dataaccess.entity.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RequestFieldRepositoryImpl implements RequestFieldRepository {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;
    private final RequestRepository requestRepository;

    @Autowired
    public RequestFieldRepositoryImpl(JdbcTemplate jdbcTemplate, RequestRepository requestRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.requestRepository = requestRepository;
        inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_request_field_values")
                .usingGeneratedKeyColumns("requestFieldValueID");
    }

    @Override
    public List<RequestField> getFields(Long requestId) {
        return jdbcTemplate.query(queryByRequestId, new Object[]{requestId}, this::buildRequestField);
    }

    @Override
    public Long createRequestField(RequestField requestField){
        Map<String, Object> params = new HashMap<>();
        params.put("fieldCode", requestField.getCode());
        params.put("requestID", requestField.getRequest().getId());
        params.put("fieldValue", requestField.getValue());
        params.put("fieldFile", requestField.getFile());
        return (Long) inserter.executeAndReturnKey(params);
    }

    private final String queryByRequestId = "SELECT * FROM (" +
            "t_request_field_values rfv INNER JOIN " +
            "t_request_field_definitions rfd ON " +
            "rfv.fieldCode = rfd.fieldCode) " +
            "WHERE rfv.requestID = ?";

    private RequestField buildRequestField(ResultSet rs, int rowNum) throws SQLException {
        Request request = requestRepository.findById(rs.getInt(3));
        return new RequestField(
                request,
                rs.getString(2),
                rs.getBoolean(5),
                rs.getString(4),
                rs.getString(7),
                rs.getBlob(8).getBytes(1, (int) rs.getBlob(8).length())
        );
    }
}
