package be.unamur.hermes.business.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;

public interface UserAccountService extends UserDetailsService {

    public static UserType retrieveUserType(String userName) {
	int index = userName.lastIndexOf("_");
	String userTypeString = userName.substring(index + 1);
	return UserType.getStatus(userTypeString);
    }

    public static String retrieveNationalRegistrationNb(String userName) {
	int index = userName.lastIndexOf("_");
	return userName.substring(0, index);
    }

    void updateCitizenAccount(long citizenId, UpdateUserAccount update);

}
