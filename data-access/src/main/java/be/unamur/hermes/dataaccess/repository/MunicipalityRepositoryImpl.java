package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Municipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MunicipalityRepositoryImpl implements MunicipalityRepository {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;
    private final AddressRepository addressRepository;


    @Autowired
    public MunicipalityRepositoryImpl(JdbcTemplate jdbcTemplate,
                                      AddressRepository addressRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.addressRepository = addressRepository;
        inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_municipalities")
                .usingGeneratedKeyColumns("municipalityID");
    }


    @Override
    public long create(Municipality municipality) {
        long addressID = addressRepository.create(municipality.getAddress());
        Map<String, Object> params = new HashMap<>();
        params.put("name", municipality.getName());
        params.put("address", addressID);
        params.put("email", municipality.getEmail());
        params.put("phone", municipality.getPhone());
        params.put("mayorName", municipality.getMayorName());
        return (Long) inserter.executeAndReturnKey(params);
    }

    @Override
    public Municipality findByName(String name) {
        return jdbcTemplate.queryForObject(queryByName, new Object[] {name}, this :: municipalityBuilder);
    }

    @Override
    public Municipality findById(long municipalityID) {
        return jdbcTemplate.queryForObject(queryById, new Object[] {municipalityID}, this :: municipalityBuilder);
    }


    // SQL requests

    private static final String queryById = "SELECT * FROM t_municipalities m WHERE m.municipalityID = ?";
    private static final String queryByName = "SELECT * FROM t_municipalities m WHERE m.name = ?";

    private Municipality municipalityBuilder(ResultSet rs, int rowNum) throws SQLException {
        return new Municipality(
                rs.getLong(1),
                rs.getString(2),
                addressRepository.findById(rs.getLong(3)),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
                );
    }

}
