package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class CitizenRepositoryImpl implements CitizenRepository {

    // queries
    private static final String queryById = //
            "SELECT * FROM t_citizens i WHERE i.employeeID = ? ";

    private static final String queryByName = //
            "SELECT * FROM t_citizens i WHERE i.firstname = ? AND i.lastname = ?";

    private static final String queryAll = //
            "SELECT * FROM t_citizens";

    private static final String createNew = //
            "INSERT INTO t_addresses (" +
                    "firstName, lastName, addressID, mail, phone, " +
                    "nationalRegistreNb, birthdate, activated) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, FALSE)";

    private static final BeanPropertyRowMapper<Citizen> citizenMapper = new BeanPropertyRowMapper<>(Citizen.class);

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;

    @Autowired
    public CitizenRepositoryImpl(final  JdbcTemplate jdbcTemplate, final AddressRepository addressRepository){
           this.jdbcTemplate = jdbcTemplate;
           this.addressRepository = addressRepository;
    }

    @Override
    public Citizen findByName(String firstname, String lastname) {
        return jdbcTemplate.queryForObject(
                queryByName,
                new Object[]{firstname, lastname},
                citizenMapper
        );
    }

    @Override
    public Citizen findById(Long citizenId) {
        return jdbcTemplate.queryForObject(
                queryById,
                new Object[]{citizenId},
                citizenMapper
        );
    }

    @Override
    public List<Citizen> findAll() {
        return jdbcTemplate.query(queryAll,citizenMapper);
    }

    @Override
    public void create(Citizen citizen) {
        long addressID = addressRepository.create(citizen.getAddress());
        Object[] values = {
                citizen.getFirstName(),
                citizen.getLastName(),
                addressID,
                citizen.getMail(),
                citizen.getPhone(),
                citizen.getNationalRegistreNb(),
                citizen.getBirthdate(),
        };

        int[] types = {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.DATE};
        jdbcTemplate.update(createNew, values, types);
    }
}
