package be.unamur.hermes.dataaccess.entity;
import be.unamur.hermes.common.enums.ScopeId;

import java.util.List;

public class Scope {

    private ScopeId name;
    private String description;
    private List<Skill> skills;

    public Scope() {};

    public Scope(ScopeId name, String description, List<Skill>skills) {
        this.name = name;
        this.description = description;
        this.skills = skills;
    }

    public ScopeId getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setName(ScopeId name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + skills +
                '}';
    }
}
