package be.unamur.hermes.common.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Authority {

    USER("USER"), OFFICER("OFFICER"), ADMIN("ADMIN");

    private final String role;

    private Authority(String role) {
	this.role = role;
    }

    @JsonValue
    public String getRole() {
	return role;
    }

    public GrantedAuthority getAuthority() {
	return new SimpleGrantedAuthority("ROLE_" + role);
    }

    public static Authority fromString(String value) {
	if (value == null)
	    return null;
	for (Authority authority : values()) {
	    if (value.toUpperCase().endsWith(authority.role))
		return authority;
	}
	return null;
    }
}
