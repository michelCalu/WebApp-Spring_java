package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.Authority;
import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.common.util.PasswordUtil;
import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.dataaccess.repository.EmployeeRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserAccountRepository accountRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserAccountRepository accountRepository) {
	this.employeeRepository = employeeRepository;
	this.accountRepository = accountRepository;
    }

    @Override
    public Employee findByName(String firstName, String lastname) throws BusinessException {
	try {
	    return employeeRepository.findByName(firstName, lastname);
	} catch (EmptyResultDataAccessException e) {
	    throw new BusinessException("Employee not found !");
	}
    }

    @Override
    public Employee findById(Long employeeId) throws BusinessException {
	try {
	    return employeeRepository.findById(employeeId);
	} catch (EmptyResultDataAccessException e) {
	    throw new BusinessException("Employee not found !");
	}
    }

    @Override
    public List<Employee> findAll() {
	return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public long register(Employee employee) {
	if (!StringUtils.hasText(employee.getPassword()))
	    throw new BusinessException("Password is required");
	// create user account (employees do not have to get activated)
	String pass = PasswordUtil.encode(employee.getPassword());
	UserAccount citizenAccount = new UserAccount(0L, 0L, employee.getNationalRegisterNb(), UserType.EMPLOYEE,
		UserStatus.ACTIVE, pass, UserAccount.prepareAuthorities(Authority.USER.getAuthority()));
	long userAccountId = accountRepository.create(citizenAccount);
	return employeeRepository.create(employee, userAccountId);
    }

	@Override
	public void suspendAccount(long employeeID) throws BusinessException {
		long acctId;
		UserAccount account;
		Employee employee;
    	try {
			 employee = employeeRepository.findById(employeeID);
			 account = employeeRepository.findAccount(employee.getNationalRegisterNb());
		}catch (EmptyResultDataAccessException e){
			throw new BusinessException("Employee not found !");
		}
		acctId = account.getAccountUserId();
    	if(!account.getStatus().getValue().equals("disabled")) {
			UpdateUserAccount update = new UpdateUserAccount();
			update.setStatus("disabled");
			accountRepository.update(acctId, update);
		}else{
    		throw new BusinessException("Account is already disabled");
		}
	}

	@Override
    public UserAccount findAccount(String nationalRegistrationNb) throws BusinessException {
	try {
	    return employeeRepository.findAccount(nationalRegistrationNb);
	} catch (EmptyResultDataAccessException e) {
	    throw new BusinessException("Account not found !");
	}
    }


}
