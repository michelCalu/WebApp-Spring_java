package be.unamur.hermes.common.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Authority {

    USER("USER"), OFFICER("OFFICER"), ADMIN("ADMIN");

    private final String role;

    private Authority(String role) {
	this.role = role;
    }

    public String getRole() {
	return role;
    }

    public GrantedAuthority getAuthority() {
	return new SimpleGrantedAuthority("ROLE_" + role);
    }

}
