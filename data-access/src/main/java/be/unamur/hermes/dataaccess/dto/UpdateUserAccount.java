package be.unamur.hermes.dataaccess.dto;

public class UpdateUserAccount {

    private String status;
    private String password;

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}