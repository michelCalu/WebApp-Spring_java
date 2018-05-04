package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.RequestStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RequestStatusRepositoryImpl implements RequestStatusRepository{

    private JdbcTemplate jdbcTemplate;

    public RequestStatusRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RequestStatus getStatusByName(String name) {
        return jdbcTemplate.queryForObject(queryByName, new Object[] {name}, this :: requestStatusBuilder);
    }

    @Override
    public RequestStatus getStatusById(Long id) {
        return jdbcTemplate.queryForObject(queryById, new Object[] {id}, this :: requestStatusBuilder);
    }


    private final String queryById = "SELECT * FROM t_req_statusses rs WHERE rs.statusID = ?";

    private final String queryByName = "SELECT * FROM t_req_statusses rs WHERE rs.statusName = ?";

    private RequestStatus requestStatusBuilder(ResultSet rs, int rowNum) throws SQLException {
        return new RequestStatus(rs.getLong(1), rs.getString(2));
    }
}
