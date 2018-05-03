package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.UserAccount;

@Repository
public class CitizenRepositoryImpl implements CitizenRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;
    private final MunicipalityRepository municipalityRepository;
    private final SimpleJdbcInsert citizenInserter;

    @Autowired
    public CitizenRepositoryImpl(final JdbcTemplate jdbcTemplate,
                                 final AddressRepository addressRepository,
                                 final MunicipalityRepository municipalityRepository) {
	this.jdbcTemplate = jdbcTemplate;
	this.addressRepository = addressRepository;
	this.municipalityRepository = municipalityRepository;
	this.citizenInserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_citizens")
		.usingGeneratedKeyColumns("citizenID");
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
    public long create(Citizen citizen, long userAccountId) {
	long addressID = addressRepository.create(citizen.getAddress());
	Map<String, Object> params = new HashMap<>();
	params.put("firstName", citizen.getFirstName());
	params.put("lastName", citizen.getLastName());
	params.put("addressID", addressID);
	params.put("mail", citizen.getMail());
	params.put("phone", citizen.getPhone());
	params.put("nationalRegisterNb", citizen.getNationalRegisterNb());
	params.put("birthdate", citizen.getBirthdate());
	params.put("userAccountID", userAccountId);
	return (Long) citizenInserter.executeAndReturnKey(params);
    }

    @Override
    public UserAccount findAccount(String nationalRegistrationNb) {
	return jdbcTemplate.queryForObject(queryAccountByNRN, new Object[] { nationalRegistrationNb },
		this::buildAccount);
    }

    @Override
    public UserAccount findAccount(long citizenId) {
	return jdbcTemplate.queryForObject(queryAccountByCitizenId, new Object[] { citizenId }, this::buildAccount);
    }

    // Queries
    private static final String queryById = "SELECT * FROM t_citizens c WHERE c.citizenID = ?";

    private static final String queryByName = "SELECT * FROM t_citizens c WHERE c.firstname = ? AND c.lastname = ?";

    private static final String queryAccountByNRN = //
	    "SELECT c.citizenID, c.userAccountID, c.nationalRegisterNb, ua.roles, ua.userStatus, ua.password FROM t_citizens c"
		    + " JOIN t_user_accounts ua ON c.userAccountID = ua.userAccountID WHERE c.nationalRegisterNb = ?";

    private static final String queryAccountByCitizenId = //
	    "SELECT c.citizenID, c.userAccountID, c.nationalRegisterNb, ua.roles, ua.userStatus, ua.password FROM t_citizens c"
		    + " JOIN t_user_accounts ua ON c.userAccountID = ua.userAccountID WHERE c.citizenID = ?";

    private static final String queryAll = "SELECT * FROM t_citizens";

    private static final String queryPending = //
	    "SELECT * FROM t_citizens c JOIN t_user_accounts ua ON c.userAccountID = ua.userAccountID WHERE ua.userStatus = 'CREATED'";

    // Other methods
    private Citizen buildCitizen(ResultSet rs, int rowNum) throws SQLException {
	return new Citizen(
	        rs.getLong(1),
            rs.getString(2),
            rs.getString(3),
            addressRepository.findById(rs.getLong(4)),
		    rs.getString(5),
            rs.getString(6),
            rs.getString(7),
            rs.getDate(8).toLocalDate());
    }

    private UserAccount buildAccount(ResultSet rs, int rowNum) throws SQLException {
	long technicalId = rs.getLong(1);
	long userAccountId = rs.getLong(2);
	String nrn = rs.getString(3);
	String rolesString = rs.getString(4);
	UserStatus userStatus = UserStatus.getStatus(rs.getString(5));
	String password = rs.getString(6);
	return new UserAccount(userAccountId, technicalId, nrn, UserType.CITIZEN, userStatus, password,
		UserAccount.prepareAuthorities(rolesString));
    }
}
