package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Inhabitant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InhabitantRepositoryImpl implements InhabitantRepository {

    // queries
    private static final String queryById = //
            "SELECT * FROM t_inhabitants i WHERE i.employeeID = ? ";

    private static final String queryByName = //
            "SELECT * FROM t_inhabitants i WHERE i.firstname = ? AND i.lastname = ?";

    private static final String queryAll = //
            "SELECT * FROM t_inhabitants";

    private static final BeanPropertyRowMapper<Inhabitant> inhabitantMapper = new BeanPropertyRowMapper<>(Inhabitant.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InhabitantRepositoryImpl(final  JdbcTemplate jdbcTemplate){
           this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Inhabitant findByName(String firstname, String lastname) {
        return jdbcTemplate.queryForObject(
                queryByName,
                new Object[]{firstname, lastname},
                inhabitantMapper
        );
    }

    @Override
    public Inhabitant findById(Long inhabitantId) {
        return jdbcTemplate.queryForObject(
                queryById,
                new Object[]{inhabitantId},
                inhabitantMapper
        );
    }

    @Override
    public List<Inhabitant> findAll() {
        return jdbcTemplate.query(queryAll,inhabitantMapper);
    }

    @Override
    public void create(String firstname, String lastname) {
        long peopleID = jdbcTemplate.update(
                "INSERT INTO t_people (firstname, lastname) VALUES (?, ?)",
                firstname,
                lastname
        );
        jdbcTemplate.update(
                "INSERT INTO t_inhabitants (peopleID) VALUES (?)",
                peopleID
        );
    }
}
