package be.unamur.hermes.dataaccess.repository;

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
            "SELECT * FROM t_companies comp JOIN t_user_accounts acc ON comp.userAccountID = acc.userAccountID WHERE acc.userStatus = 'CREATED'";


    @Autowired
    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate, AddressRepository addressRepository) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.addressRepository = addressRepository;
	this.companyInserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_companies");
    }

    @Override
    public long create(Company company) {
	long address = addressRepository.create(company.getAddress());
	Map<String, Object> params = new HashMap<>();
	params.put("companyNb", company.getCompanyNb());
	params.put("vatNb", company.getVatNb());
	params.put("address", address);
	params.put("contactPerson", company.getContactPerson());
	params.put("legalForm", company.getLegalForm());
	return (Long) companyInserter.executeAndReturnKey(params);
    }


    @Override
    public Company findByCompanyNb(String companyNb) {
	return jdbcTemplate.queryForObject(queryByCompanyNb, new Object[] { companyNb }, this::buildCompany);
    }

    @Override
    public List<Company> findAll()  {
        return jdbcTemplate.query(queryAll, this::buildCompany);
    }

    @Override
    public List<Company> findPending() {
        return jdbcTemplate.query(queryPending, this::buildCompany);
    }


    private Company buildCompany(ResultSet rs, int row) throws SQLException {
	Address address = addressRepository.findById(rs.getLong(3));
	return new Company(
	        rs.getString(1),
            rs.getString(2),
            address,
            rs.getString(4),
            rs.getString(5));
    }



}
