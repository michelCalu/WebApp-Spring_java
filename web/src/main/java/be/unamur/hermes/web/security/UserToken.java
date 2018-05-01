package be.unamur.hermes.web.security;

public class UserToken {

    private String token;
    private Long id;

    public UserToken() {
    }

    public UserToken(String access_token, Long technicalId) {
	this.token = access_token;
	this.id = technicalId;
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
}
