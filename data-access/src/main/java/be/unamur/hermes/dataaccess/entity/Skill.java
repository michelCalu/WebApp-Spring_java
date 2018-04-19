package be.unamur.hermes.dataaccess.entity;

public class Skill {

    private String name;
    private String description;

    public Skill(){};

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name=" + name +
                ", description='" + description + '\'' +
                '}';
    }
}
