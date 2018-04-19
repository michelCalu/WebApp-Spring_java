package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Skill;

public interface SkillRepository {

    public long create();

    public Skill findById(long id);

}
