package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.dataaccess.dto.UpdateCompanyAccount;
import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Company;
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
public class CompanyRepositoryImpl implements CompanyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert companyInserter;
    private final AddressRepository addressRepository;


    // queries
    private static final String queryByCompanyNb = //
	    "SELECT * FROM t_companies WHERE companyNb = ?";


    private static final String queryAll = //
            "SELECT * FROM t_companies";

    private static final String queryPending = //
            "SELECT * FROM t_companies  WHERE address in (SELECT addressID from t_addresses WHERE zipCode in"+
                    "(SELECT zipCode from t_addresses WHERE addressID in ("+
                    "SELECT address from t_municipalities WHERE municipalityID= ?)))"+
                    "AND companyStatus='created'";

    private static final String updateCompanyStatus = //
            "UPDATE t_companies c SET c.companyStatus = ? WHERE c.companyNb = ?";


    @Autowired
    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate, AddressRepository addressRepository) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.addressRepository = addressRepository;
	this.companyInserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_companies");
    }

    @Override
    public void create(Company company) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyNb", company.getCompanyNb());
        params.put("vatNb", company.getVatNb());
        params.put("address", company.getAddress().getId());
        params.put("legalForm", company.getLegalForm());
        params.put("contactPerson", company.getContactPerson());
        params.put("companyName", company.getCompanyName());
        params.put("companyStatus", UserStatus.CREATED);
        companyInserter.execute(params);
    }

    @Override
    public List<Company> findPending(long municipalityID) {
       // return jdbcTemplate.query(queryPending, new Object[] { municipalityID }, this::buildCompany);
        return jdbcTemplate.query(queryPending, new Object[] { municipalityID },
                this::buildCompany);
    }

    @Override
    public void activate(String companyNb, UpdateCompanyAccount updates) {
        jdbcTemplate.update(String.format(updateCompanyStatus, "companyStatus"), updates.getCompanyStatus(), companyNb);
    }


    @Override
    public Company findByCompanyNb(String companyNb) {
	return jdbcTemplate.queryForObject(queryByCompanyNb, new Object[] { companyNb }, this::buildCompany);
    }

    @Override
    public List<Company> findAll()  {
        return jdbcTemplate.query(queryAll, this::buildCompany);
    }


    private Company buildCompany(ResultSet rs, int row) throws SQLException {
	Address address = addressRepository.findById(rs.getLong(3));
	UserStatus companyStatus = UserStatus.getStatus(rs.getString(7));
	return new Company(
	        rs.getString(1),
            rs.getString(2),
            address,
            rs.getString(4),
            rs.getString(5),
            rs.getString(6),
            companyStatus
    );
    }



}
