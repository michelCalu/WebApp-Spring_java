package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Inhabitant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InhabitantRepositoryImpl implements InhabitantRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InhabitantRepositoryImpl(final  JdbcTemplate jdbcTemplate){
           this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Inhabitant findByName(String firstname, String lastname) {
        return jdbcTemplate.queryForObject(
                "SELECT p.peopleID, p.firstname, p.lastname, i.inhabitantID, i.activated FROM " +
                        "t_people p, t_inhabitants i WHERE " +
                        "p.peopleID = i.peopleID AND p.firstname = ? AND p.lastname = ?",
                new Object[]{firstname, lastname},
                (rs, rowNum) -> new Inhabitant(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getBoolean(5))
        );
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
