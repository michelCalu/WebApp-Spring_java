package be.unamur.hermes.dataaccess.entity;

import be.unamur.hermes.common.enums.SkillId;

public class Skill {

    private SkillId id;
    private String description;

    public Skill(SkillId id, String description) {
        this.id = id;
        this.description = description;
    }

    public SkillId getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(SkillId id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
