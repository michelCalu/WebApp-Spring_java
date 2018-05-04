package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.RequestField;
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
public class RequestFieldRepositoryImpl implements RequestFieldRepository {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;

    @Autowired
    public RequestFieldRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_request_field_values")
                .usingGeneratedKeyColumns("requestFieldValueID");
    }

    @Override
    public List<RequestField> getFields(Long requestId) {
        return jdbcTemplate.query(queryByRequestId, new Object[]{requestId}, this::buildRequestField);
    }

    @Override
    public Long createRequestField(RequestField requestField, Long requestId){
        Map<String, Object> params = new HashMap<>();
        params.put("requestID", requestId);
        params.put("fieldCode", requestField.getCode());
        params.put("fieldValue", requestField.getFieldValue());
        params.put("fieldFile", requestField.getFieldFile());
        return (Long) inserter.executeAndReturnKey(params);
    }

    private final String queryByRequestId = "SELECT * FROM (" +
            "t_request_field_values rfv INNER JOIN " +
            "t_request_field_definitions rfd ON " +
            "rfv.fieldCode = rfd.fieldCode) " +
            "WHERE rfv.requestID = ?";

    private RequestField buildRequestField(ResultSet rs, int rowNum) throws SQLException {
        return new RequestField(
                rs.getString(2),
                rs.getBoolean(5),
                rs.getString(4),
                rs.getString(7),
                rs.getBlob(8).getBytes(1, (int) rs.getBlob(8).length())
        );
    }
}
