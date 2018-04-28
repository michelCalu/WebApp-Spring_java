package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    public AddressRepositoryImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address findById(Long addressID) {
	return jdbcTemplate.queryForObject(queryById, new Object[] { addressID }, this::buildAddress);
    }

    @Override
    public long create(Address address) {
	Object[] values = { address.getCountry(), address.getState(), address.getZipCode(), address.getStreet(),
		address.getStreetNb() };
	int[] types = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER };
	return jdbcTemplate.update(createNew, values, types);
    }

    // queries
    private static final String queryById = //
	    "SELECT * FROM t_addresses a WHERE a.addressID = ?";

    private static final String createNew = //
	    "INSERT INTO t_addresses (country, state, " + "zipCode, street, streetNb) VALUES " + "(?, ?, ?, ?, ?)";

    // Other methods
    private Address buildAddress(ResultSet rs, int rowNum) throws SQLException {
	return new Address(rs.getLong(COL_ID), rs.getString(COL_STREET), rs.getInt(COL_STREETNB),
		rs.getString(COL_NBSUFFIX), rs.getInt(COL_ZIPCODE), rs.getString(COL_MUNICIPALITY),
		rs.getString(COL_STATE), rs.getString(COL_COUNTRY));
    }
}
