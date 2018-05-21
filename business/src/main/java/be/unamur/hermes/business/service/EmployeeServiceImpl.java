package be.unamur.hermes.business.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.Authority;
import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.common.util.PasswordUtil;
import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.dataaccess.repository.EmployeeRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService, Errors {

    private final EmployeeRepository employeeRepository;
    private final UserAccountRepository accountRepository;
    private final AddressService addressService;
    private final MunicipalityService municipalityService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserAccountRepository accountRepository,
	    AddressService addressService, MunicipalityService municipalityService) {
	this.employeeRepository = employeeRepository;
	this.accountRepository = accountRepository;
	this.addressService = addressService;
	this.municipalityService = municipalityService;
    }

    @Override
    public Employee findByName(String firstName, String lastname) throws BusinessException {
	return employeeRepository.findByName(firstName, lastname);
    }

    @Override
    public Employee findById(Long employeeId) throws BusinessException {
	return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> findAll() {
	return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public long register(Employee employee) {
	checkEmployeeAttributes(employee);

	// Update and creation of the employee address
		addressService.createAddress(employee.getAddress());

	// create user account (employees do not have to get activated)
	String pass = PasswordUtil.encode(employee.getPassword());
	UserAccount employeeAccount = new UserAccount(0L, 0L, employee.getNationalRegisterNb(), UserType.EMPLOYEE,
		UserStatus.ACTIVE, pass,
		Arrays.asList(Authority.USER.getAuthority(), Authority.OFFICER.getAuthority()));
	long userAccountId = accountRepository.create(employeeAccount);
	return employeeRepository.create(employee, userAccountId);
    }

    private void checkEmployeeAttributes(Employee employee) throws BusinessException {
	if (!StringUtils.hasText(employee.getPassword()))
	    throw new BusinessException(MISSING_PASSWORD, "Password is required");
	if (employee.getBirthdate().isAfter(employee.getArrivalDate())) {
	    throw new BusinessException(INVALID_BIRTH_DATE, "birthdate cannot be after arrival date");
	}
    }

    @Override
    public void suspendAccount(long employeeID) throws BusinessException {
	long acctId;
	UserAccount account;
	Employee employee;
	employee = employeeRepository.findById(employeeID);
	account = employeeRepository.findAccount(employee.getNationalRegisterNb());
	acctId = account.getAccountUserId();
	if (account.getStatus() != UserStatus.DISABLED) {
	    UpdateUserAccount update = new UpdateUserAccount();
	    update.setStatus("disabled");
	    accountRepository.update(acctId, update);
	} else {
	    throw new BusinessException(INVALID_USER_STATUS, "Account is already disabled");
	}
    }

    @Override
    public UserAccount findAccount(String nationalRegistrationNb) throws BusinessException {
	return employeeRepository.findAccount(nationalRegistrationNb);
    }

    @Override
    public UserAccount findAccount(long employeeId) {
	return employeeRepository.findAccount(employeeId);
    }
}
