package be.unamur.hermes.web.security;

import java.util.ArrayList;
import java.util.List;

import be.unamur.hermes.common.enums.Authority;

public class UserToken {

    private String token;
    private Long id;
    private List<Authority> roles = new ArrayList<>();

    public UserToken() {
    }

    public UserToken(String access_token, Long technicalId, List<Authority> roles) {
	this.token = access_token;
	this.id = technicalId;
	this.roles = roles;
    }

    public String getToken() {
	return token;
    }

    public void setToken(String access_token) {
	this.token = access_token;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long technicalId) {
	this.id = technicalId;
    }

    public List<Authority> getRoles() {
	return roles;
    }

    public void setRoles(List<Authority> roles) {
	this.roles = roles;
    }
}
