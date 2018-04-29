package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.UserAccount;

@Repository
public class CitizenRepositoryImpl implements CitizenRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;

    @Autowired
    public CitizenRepositoryImpl(final JdbcTemplate jdbcTemplate, final AddressRepository addressRepository) {
	this.jdbcTemplate = jdbcTemplate;
	this.addressRepository = addressRepository;
    }

    @Override
    public Citizen findByName(String firstname, String lastname) {
	return jdbcTemplate.queryForObject(queryByName, new Object[] { firstname, lastname }, this::buildCitizen);
    }

    @Override
    public Citizen findById(long citizenId) {
	return jdbcTemplate.queryForObject(queryById, new Object[] { citizenId }, this::buildCitizen);
    }

    @Override
    public List<Citizen> findAll() {
	return jdbcTemplate.query(queryAll, this::buildCitizen);
    }

    @Override
    public List<Citizen> findPending() {
	return jdbcTemplate.query(queryPending, this::buildCitizen);
    }

    @Override
    public long create(Citizen citizen) {
	long addressID = addressRepository.create(citizen.getAddress());
	Object[] values = { citizen.getFirstName(), citizen.getLastName(), addressID, citizen.getMail(),
		citizen.getPhone(), citizen.getNationalRegisterNb(), citizen.getBirthdate() };

	// TODO add default value for status
	int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		Types.VARCHAR };
	return jdbcTemplate.update(createNew, values, types);
    }

    @Override
    public void activate(long citizenId) {
	jdbcTemplate.update(updateActivate, citizenId);
    }

    @Override
    public UserAccount findAccount(String nationalRegistrationNb) {
	return jdbcTemplate.queryForObject(queryAccountByNRN, new Object[] { nationalRegistrationNb },
		this::buildAccount);
    }

    // Queries
    private static final String queryById = "SELECT * FROM t_citizens c WHERE c.citizenID = ?";

    private static final String queryByName = "SELECT * FROM t_citizens c WHERE c.firstname = ? AND c.lastname = ?";

    private static final String queryAccountByNRN = //
	    "SELECT c.citizenID, c.userAccountID, c.nationalRegisterNb, ua.roles, ua.userStatus, ua.password FROM t_citizens c"
		    + " JOIN t_user_accounts ua ON c.userAccountID = ua.userAccountID WHERE c.nationalRegisterNb = ?";

    private static final String queryAll = "SELECT * FROM t_citizens";

    private static final String queryPending = "SELECT * FROM t_citizens c WHERE c.activated = FALSE";

    private static final String createNew = "INSERT INTO t_citizens (" + "firstName, lastName, addressID, mail, phone, "
	    + "nationalRegisterNb, birthdate, userStatus) VALUES " + "(?, ?, ?, ?, ?, ?, ?, FALSE)";

    private static final String updateActivate = "UPDATE t_citizens c SET c.activated = TRUE WHERE c.id = ?";

    // Other methods
    private Citizen buildCitizen(ResultSet rs, int rowNum) throws SQLException {
	return new Citizen(rs.getLong(1), rs.getString(2), rs.getString(3), addressRepository.findById(rs.getLong(4)),
		rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8).toLocalDate());
    }

    private UserAccount buildAccount(ResultSet rs, int rowNum) throws SQLException {
	long technicalId = rs.getLong(1);
	long userAccountId = rs.getLong(2);
	String nrn = rs.getString(3);
	String[] roles = rs.getString(4).split(",");
	UserStatus userStatus = UserStatus.getStatus(rs.getString(5));
	String password = rs.getString(6);
	return new UserAccount(userAccountId, technicalId, nrn, UserType.CITIZEN, userStatus, password,
		Arrays.asList(roles));
    }
}
