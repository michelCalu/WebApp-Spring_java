package be.unamur.hermes.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum SkillId {

    ETAT_CIVIL("etatCivil"),
    POPULATION("population"),
    STATIONNEMENT("population");
   ;

    private final String id;

    private SkillId(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @JsonCreator
    public static SkillId getSkillId(String skillIdString) {
        if (!StringUtils.hasText(skillIdString))
            return null;
        for (SkillId skillId : SkillId.values()) {
            if (skillId.id.equalsIgnoreCase(skillIdString))
                return skillId;
        }
        return null;
    }
}