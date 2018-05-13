package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.common.enums.MandataryRole;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Mandatary;
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
public class MandataryRepositoryImpl implements MandataryRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    private final CompanyRepository companyRepository;
    private final CitizenRepository citizenRepository;

    // queries
    private static final String queryBase = //
	    "SELECT * FROM t_mandataries";
    private static final String queryById = //
	    queryBase + " WHERE mandataryID = ?";
    private static final String queryByCitizenId = //
	    queryBase + " WHERE citizenID = ?";
    private static final String queryByCompanyNb = //
	    queryBase + " WHERE companyNb = ?";
    private static final String queryByCompanyRole = //
	    queryByCompanyNb + " AND role = ?";

    @Autowired
    public MandataryRepositoryImpl(JdbcTemplate jdbc, CompanyRepository companyRepository,
	    CitizenRepository citizenRepository) {
	super();
	this.companyRepository = companyRepository;
	this.citizenRepository = citizenRepository;
	this.jdbc = jdbc;
	this.inserter = new SimpleJdbcInsert(jdbc.getDataSource()).withTableName("t_mandataries")
		.usingGeneratedKeyColumns("mandataryID");
    }

    @Override
    public List<Mandatary> getMandatariesByCompanyNb(String companyNb) {
	return jdbc.query(queryByCompanyNb, this::build, new Object[] { companyNb });
    }

    @Override
    public List<Mandatary> getMandataries(String companyNb, String role) {
	return jdbc.query(queryByCompanyRole, this::build, new Object[] { companyNb, role });
    }

    @Override
    public Mandatary findById(long id) {
	return jdbc.queryForObject(queryById, new Object[] { id }, this::build);
    }

    @Override
    public long create(Mandatary mandatary) {
	Map<String, Object> params = new HashMap<>();
	params.put("citizenID", mandatary.getCitizen().getId());
	params.put("companyNb", mandatary.getCompany().getCompanyNb());
	params.put("role", mandatary.getRole().getValue());
	return (Long) inserter.executeAndReturnKey(params);
    }

    @Override
    public List<Mandatary> findByCitizenId(long citizenId) {
	return jdbc.query(queryByCitizenId, this::build, new Object[] { citizenId });
    }

    @Override
    public List<Mandatary> findAll() {
	return jdbc.query(queryBase, this::build);
    }

    private Mandatary build(ResultSet rs, int row) throws SQLException {
	Citizen ctz = citizenRepository.findById(rs.getLong(2));
	Company company = companyRepository.findByCompanyNb(rs.getString(3));
	MandataryRole role = MandataryRole.getRole(rs.getString(4));
	return new Mandatary(rs.getLong(1), ctz, company, role);
    }
}
