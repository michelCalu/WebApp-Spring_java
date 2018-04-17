package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class AddressRepositoryImpl implements AddressRepository {




    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public AddressRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address findById(Long addressID) {
        return jdbcTemplate.queryForObject(queryById, new Object[] {addressID}, this::buildAddress);
    }

    @Override
    public long create(Address address) {
        Object[] values = {
                address.getCountry(),
                address.getState(),
                address.getZipCode(),
                address.getStreet(),
                address.getStreetNb() };
        int[] types = {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.INTEGER};
        return jdbcTemplate.update(createNew, values, types);
    }

    //queries
    private static final String queryById = //
            "SELECT * FROM t_addresses a WHERE a.addressID = ?";

    private static final String createNew = //
            "INSERT INTO t_addresses (country, state, " +
                    "zipCode, street, streetNb) VALUES " +
                    "(?, ?, ?, ?, ?)";


    // Other methods
    private Address buildAddress (ResultSet rs, int rowNum) throws SQLException {
        return new Address(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getInt(6));
    }
}
