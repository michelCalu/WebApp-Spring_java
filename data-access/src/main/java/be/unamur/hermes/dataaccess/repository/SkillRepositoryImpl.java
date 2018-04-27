package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Skill;
import org.springframework.stereotype.Repository;

@Repository
public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public long create() {
        return 0;
    }

    @Override
    public Skill findById(long id) {
        return null;
    }
}
