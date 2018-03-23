package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.PeopleBis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PeopleBisRepositoryImpl implements PeopleBisRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleBisRepositoryImpl(final  JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PeopleBis> findAll(){
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(
                "SELECT * FROM t_people");

        List<PeopleBis> peopleBiss = new ArrayList<>();
        for(Map row: rows){
            PeopleBis peopleBis = new PeopleBis(
                    (String)(row.get("firstname")),
                    (String)(row.get("lastname")));
            peopleBiss.add(peopleBis);
        }
        return  peopleBiss;
    }
}
