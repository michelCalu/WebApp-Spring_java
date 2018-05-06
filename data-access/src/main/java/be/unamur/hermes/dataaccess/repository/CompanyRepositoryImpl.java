package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Company;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;
    private final AddressRepository addressRepository;

    // queries
    private static final String queryById = //
	    "SELECT * FROM t_companies WHERE companyNb = ?";
    private static final String queryByVat = //
	    "SELECT * FROM t_companies WHERE vatNb = ?";

    @Autowired
    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate, AddressRepository addressRepository) {
	super();
	this.jdbc = jdbcTemplate;
	this.addressRepository = addressRepository;
	this.inserter = new SimpleJdbcInsert(jdbc.getDataSource()).withTableName("t_companies");
    }

    @Override
    public String create(Company company) {
	long address = addressRepository.create(company.getAddress());
	Map<String, Object> params = new HashMap<>();
	params.put("companyNb", company.getCompanyNb());
	params.put("vatNb", company.getVatNb());
	params.put("address", address);
	params.put("contactPerson", company.getContactPerson());
	params.put("legalForm", company.getLegalForm());
	inserter.execute(params);
	return company.getCompanyNb();
    }

    @Override
    public Company findByVAT(String vat) {
	return jdbc.queryForObject(queryByVat, new Object[] { vat }, this::build);
    }

    @Override
    public Company findById(String companyNb) {
	return jdbc.queryForObject(queryById, new Object[] { companyNb }, this::build);
    }

    private Company build(ResultSet rs, int row) throws SQLException {
	Address address = addressRepository.findById(rs.getLong(3));
	return new Company(rs.getString(1), rs.getString(2), address, rs.getString(4), rs.getString(5));
    }
}
