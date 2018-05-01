package be.unamur.hermes.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final CitizenService citizenService;
    private final EmployeeService employeeService;
    private final UserAccountRepository accountRepository;

    @Autowired
    public UserAccountServiceImpl(CitizenService citizenService, EmployeeService employeeService,
	    UserAccountRepository accountRepository) {
	super();
	this.citizenService = citizenService;
	this.employeeService = employeeService;
	this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	String nationalRegistrationNb = UserAccountService.retrieveNationalRegistrationNb(username);
	UserType type = UserAccountService.retrieveUserType(username);
	try {
	    switch (type) {
	    case CITIZEN:
		return citizenService.findAccount(nationalRegistrationNb);
	    case EMPLOYEE:
		return employeeService.findAccount(nationalRegistrationNb);
	    default:
		final UsernameNotFoundException exception = new UsernameNotFoundException(
			"Unknown or missing user type");
		logger.error("Unknown or missing user type", exception);
		throw exception;
	    }
	} catch (BusinessException ex) {
	    logger.error("Unknown user", ex);
	    throw new UsernameNotFoundException("Unknown user", ex);
	}
    }

    @Override
    @Transactional
    public void updateCitizenAccount(long citizenId, UpdateUserAccount update) {
	UserAccount citizenAccount = citizenService.findAccount(citizenId);
	accountRepository.update(citizenAccount.getAccountUserId(), update);
    }
}
