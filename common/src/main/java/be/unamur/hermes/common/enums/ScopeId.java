package be.unamur.hermes.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum ScopeId {

    ETAT_CIVIL("etatCivil"),
    POPULATION("population"),
    MOBILITE("mobility"),
    VOIRIE("voierie");

    private final String id;

    private ScopeId(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @JsonCreator
    public static ScopeId getClaimId(String scopeIdString) {
        if (!StringUtils.hasText(scopeIdString))
            return null;
        for (ScopeId scopeId : ScopeId.values()) {
            if (scopeId.id.equalsIgnoreCase(scopeIdString))
                return scopeId;
        }
        return null;
    }
}