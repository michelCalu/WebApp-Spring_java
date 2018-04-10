package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Claim;

@Repository
public class ClaimRepositoryImpl implements ClaimRepository {

    // queries
    private static final String queryById = //
	    "SELECT cl.claimID, cl.claimTypeID, cl.employeeID, cl.citizenID, cl.status FROM t_claims cl WHERE cl.claimID = ?";
    private static final String queryByCitizenId = //
	    "SELECT cl.claimID, cl.claimTypeID, cl.employeeID, cl.citizenID, cl.status FROM t_claims cl WHERE cl.citizenID = ?";
    private static final String create = //
	    "INSERT INTO t_claims(claimTypeID, employeeID, citizenID, status) VALUES(?,?,?,?)";

    private static final BeanPropertyRowMapper<Claim> claimMapper = new BeanPropertyRowMapper<>(Claim.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClaimRepositoryImpl(JdbcTemplate jdbcTemplate) {
	super();
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Claim> findByCitizen(long citizenId) {
	return jdbcTemplate.query(queryByCitizenId, claimMapper);
    }

    @Override
    public Claim findById(long id) {
	return jdbcTemplate.queryForObject(queryById, new Object[] { id }, claimMapper);
    }

    @Override
    public long create(Claim newClaim) {
	Long citizenId = newClaim.getCitizen().getId();
	Long employeeId = newClaim.getAssignee() == null ? null : newClaim.getAssignee().getId();
	return jdbcTemplate.update(create,
		new Object[] { newClaim.getTypeId(), employeeId, citizenId, newClaim.getStatus() });
    }
}
