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

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final int COL_ID = 1;
    private final int COL_STREET = 2;
    private final int COL_STREETNB = 3;
    private final int COL_NBSUFFIX = 4;
    private final int COL_ZIPCODE = 5;
    private final int COL_MUNICIPALITY = 6;
    private final int COL_STATE = 7;
    private final int COL_COUNTRY = 8;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;

    @Autowired
    public AddressRepositoryImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
	this.inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_addresses")
		.usingGeneratedKeyColumns("addressID");
    }

    @Override
    public Address findById(Long addressID) {
	return jdbcTemplate.queryForObject(queryById, new Object[] { addressID }, this::buildAddress);
    }

    @Override
    public long create(Address address) {
	Map<String, Object> params = new HashMap<>();
	params.put("street", address.getStreet());
	params.put("streetNb", address.getStreetNb());
	params.put("nbSuffix", address.getNbSuffix());
	params.put("zipCode", address.getZipCode());
	params.put("municipality", address.getMunicipality());
	params.put("state", address.getState());
	params.put("country", address.getCountry());
	return (Long) inserter.executeAndReturnKey(params);
    }

    // queries
    private static final String queryById = //
	    "SELECT * FROM t_addresses a WHERE a.addressID = ?";

    // Other methods
    private Address buildAddress(ResultSet rs, int rowNum) throws SQLException {
	return new Address(rs.getLong(COL_ID), rs.getString(COL_STREET), rs.getInt(COL_STREETNB),
		rs.getString(COL_NBSUFFIX), rs.getInt(COL_ZIPCODE), rs.getString(COL_MUNICIPALITY),
		rs.getString(COL_STATE), rs.getString(COL_COUNTRY));
    }
}
