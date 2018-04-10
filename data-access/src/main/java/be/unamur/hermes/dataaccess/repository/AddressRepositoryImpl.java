package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    //queries
    private static final String queryById = //
        "SELECT * FROM t_addresses a WHERE a.addressID = ?";

    private static final String createNew = //
        "INSERT INTO t_addresses (country, state, " +
                "zipCode, street, streetNb) VALUES " +
                "(?, ?, ?, ?, ?)";


    private final JdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<Address> addressMapper = new BeanPropertyRowMapper<>(Address.class);

    @Autowired
    public AddressRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address findById(Long addressID) {
        return jdbcTemplate.queryForObject(queryById, new Object[] {addressID}, addressMapper);
    }

    @Override
    public long create(Address address) {
        Object[] values = {address.getCountry(), address.getState(), address.getZipCode(),
            address.getStreet(), address.getStreetNb() };
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER};
        return jdbcTemplate.update(createNew, values, types);
    }
}
