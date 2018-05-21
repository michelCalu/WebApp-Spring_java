package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Event;
import be.unamur.hermes.dataaccess.entity.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    // queries
    private static final String queryById = //
	    "SELECT * FROM t_events WHERE eventID = ?";
    private static final String queryByRequestId = //
	    "SELECT * FROM t_events WHERE request = ?";
    private static final String queryByAuthorId = //
	    "SELECT * FROM t_events WHERE author = ?";
    private static final String queryByRequestIdEventType = //
	    "SELECT * FROM t_events ev JOIN t_event_types ty ON ev.eventType = ty.eventTypeID " + //
		    "WHERE ev.request = ? AND ty.eventDesc = ?";
    private static final String queryEventTypeById = //
	    "SELECT * FROM t_event_types WHERE eventTypeID = ? ";
    private static final String queryEventTypeByDescr = //
	    "SELECT * FROM t_event_types WHERE eventDesc = ? ";

    @Autowired
    public EventRepositoryImpl(JdbcTemplate jdbc) {
	super();
	this.jdbc = jdbc;
	this.inserter = new SimpleJdbcInsert(jdbc.getDataSource()).withTableName("t_events")
		.usingGeneratedKeyColumns("eventID");
    }

    @Override
    public long create(Event event) {
	Map<String, Object> params = new HashMap<>();
	long eventTypeId = getTypeByDescription(event.getType().getDescription()).getId();
	params.put("eventType", eventTypeId);
	params.put("at", event.getAt());
	params.put("author", event.getAuthorId());
	params.put("request", event.getRequestId());
	params.put("comment", event.getComment());
	return (Long) inserter.executeAndReturnKey(params);
    }

    @Override
    public List<Event> getEventsByRequest(long requestID) {
	return jdbc.query(queryByRequestId, new Object[] { requestID }, this::build);
    }

    @Override
    public List<Event> getEventsByRequest(long requestID, String eventType) {
	return jdbc.query(queryByRequestIdEventType, new Object[] { requestID, eventType }, this::build);
    }

    @Override
    public Event getEventById(long id) {
	return jdbc.queryForObject(queryById, new Object[] { id }, this::build);
    }

    @Override
    public List<Event> getEventsByAuthor(long authorId) {
	return jdbc.query(queryByAuthorId, new Object[] { authorId }, this::build);
    }

    private EventType getTypeById(long id) {
	return jdbc.queryForObject(queryEventTypeById, new Object[] { id },
		(rs, row) -> new EventType(rs.getLong(1), rs.getString(2)));
    }

    private EventType getTypeByDescription(String description) {
	return jdbc.queryForObject(queryEventTypeByDescr, new Object[] { description },
		(rs, row) -> new EventType(rs.getLong(1), rs.getString(2)));
    }

    private Event build(ResultSet rs, int row) throws SQLException {
	EventType type = getTypeById(rs.getLong(2));
	Timestamp timestamp = rs.getTimestamp(3);
	return new Event(
	        rs.getLong(2),
            type,
            timestamp.toLocalDateTime(),
            rs.getLong(4),
            rs.getLong(5),
            rs.getString(6)
            );
    }
}
