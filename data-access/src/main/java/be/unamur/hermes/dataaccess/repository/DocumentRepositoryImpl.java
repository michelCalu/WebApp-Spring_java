package be.unamur.hermes.dataaccess.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    // queries
    private static final String findDocumentById = //
	    "SELECT contents FROM t_documents WHERE documentID = ?";

    private static final String findDocumentIdsByRequest = //
	    "SELECT documentID FROM t_documents WHERE requestID = ?";

    @Autowired
    public DocumentRepositoryImpl(JdbcTemplate jdbc) {
	super();
	this.jdbc = jdbc;
	this.inserter = new SimpleJdbcInsert(jdbc.getDataSource()).withTableName("t_documents")
		.usingGeneratedKeyColumns("documentID");
    }

    @Override
    public String getDocument(long documentId) {
	return jdbc.queryForObject(findDocumentById, String.class, documentId);
    }

    @Override
    public List<Long> getDocumentIds(long requestId) {
	return jdbc.queryForList(findDocumentIdsByRequest, Long.class, requestId);
    }

    @Override
    public long create(long requestId, String contents) {
	Map<String, Object> params = new HashMap<>();
	params.put("requestID", requestId);
	params.put("contents", contents);
	return (Long) inserter.executeAndReturnKey(params);
    }
}
