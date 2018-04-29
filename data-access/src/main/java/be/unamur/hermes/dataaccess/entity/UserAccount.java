package be.unamur.hermes.dataaccess.entity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;

public class UserAccount extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    public static String createUserName(String nationalRegistrationNb, UserType type) {
	return nationalRegistrationNb + "_" + type.getValue();
    }

    private UserStatus status;
    private final UserType type;
    private long technicalId;
    private final long accountUserId;

    public UserAccount(long accountUserId, long technicalId, String nationalRegistrationNb, UserType type,
	    UserStatus status, String password, List<String> authorities) {
	super(createUserName(nationalRegistrationNb, type), password,
		authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	this.accountUserId = accountUserId;
	this.status = status;
	this.type = type;
	this.technicalId = technicalId;
    }

    /**
     * 
     * @return the corresponding employeeId or citizenId
     */
    public long getTechnicalId() {
	return technicalId;
    }

    public void setTechnicalId(long technicalId) {
	this.technicalId = technicalId;
    }

    public UserStatus getStatus() {
	return status;
    }

    public void setStatus(UserStatus status) {
	this.status = status;
    }

    public UserType getType() {
	return type;
    }

    @Override
    public boolean isEnabled() {
	return UserStatus.ACTIVE == status;
    }
}
