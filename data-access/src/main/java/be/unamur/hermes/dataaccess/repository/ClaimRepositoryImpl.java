package be.unamur.hermes.dataaccess.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Claim;

@Repository
public class ClaimRepositoryImpl implements ClaimRepository {

    // queries
    private static final String queryById = //
	    "SELECT cl.claimID, cl.claimTypeID, cl.employeeID, cl.citizenID FROM t_claims cl WHERE cl.claimID = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClaimRepositoryImpl(JdbcTemplate jdbcTemplate) {
	super();
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Claim> findByCitizen(long citizenId) {
	// TODO Auto-generated method stub
	return Collections.emptyList();
    }

    @Override
    public Claim findById(long id) {
	return jdbcTemplate.queryForObject(queryById, new Object[] { id },
		(rs, rowNum) -> new Claim(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getLong(4)));
    }

    @Override
    public void create(long type, long citizenId) {
	// TODO Auto-generated method stub
    }
}
