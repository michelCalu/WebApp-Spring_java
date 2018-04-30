package be.unamur.hermes.common.enums;

public enum Authority {

    USER("USER"), ADMIN("ADMIN");

    private final String role;

    private Authority(String role) {
	this.role = role;
    }

    public String getRole() {
	return role;
    }

    public String getAuthority() {
	return "ROLE_" + role;
    }

}
