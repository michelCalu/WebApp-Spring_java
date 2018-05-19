package be.unamur.hermes.dataaccess.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ParameterRepositoryImpl implements ParameterRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    // queries
    private static final String queryContents = //
	    "SELECT contents FROM t_parameters WHERE requestTypeID = ? AND municipalityID = ?";
    private static final String updateContents = //
	    "UPDATE t_parameters SET contents = ? WHERE requestTypeID = ? AND municipalityID = ?";

    @Autowired
    public ParameterRepositoryImpl(JdbcTemplate jdbc) {
	super();
	this.jdbc = jdbc;
	this.inserter = new SimpleJdbcInsert(jdbc.getDataSource()).withTableName("t_parameters");
    }

    @Override
    public void create(long municipalityId, long requestTypeId, String contents) {
	Map<String, Object> params = new HashMap<>();
	params.put("requestTypeID", requestTypeId);
	params.put("municipalityID", municipalityId);
	params.put("contents", contents);
	inserter.execute(params);
    }

    @Override
    public String findContents(long municipalityId, long requestTypeId) {
	return jdbc.queryForObject(queryContents, new Object[] { requestTypeId, municipalityId }, String.class);
    }

    @Override
    public void updateContents(long municipalityId, long requestTypeId, String contents) {
	jdbc.update(updateContents, contents, requestTypeId, municipalityId);
    }
}
