package be.unamur.hermes.business.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNServiceAccessException;
import be.unamur.hermes.business.io.NRNValidationClient;
import be.unamur.hermes.business.model.NRNValidation.NRNValidationModel;
import be.unamur.hermes.common.enums.Authority;
import be.unamur.hermes.common.enums.HermesRegex;
import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.common.exception.NRNNotValidException;
import be.unamur.hermes.common.util.PasswordUtil;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.dataaccess.repository.CitizenRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;

@Service
public class CitizenServiceImpl implements CitizenService, Errors {

    private CitizenRepository citizenRepository;
    private final UserAccountRepository accountRepository;
    private final AddressService addressService;
    private final MunicipalityService municipalityService;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository, UserAccountRepository accountRepository,
	    AddressService addressService, MunicipalityService municipalityService) {
	this.citizenRepository = citizenRepository;
	this.accountRepository = accountRepository;
	this.addressService = addressService;
	this.municipalityService = municipalityService;
    }

    @Override
    public Citizen findByName(String firstName, String lastname) throws BusinessException {
	return citizenRepository.findByName(firstName, lastname);
    }

    @Override
    public Citizen findById(Long citizenId) throws BusinessException {
	return citizenRepository.findById(citizenId);
    }

    @Override
    public List<Citizen> findAll() {
	return citizenRepository.findAll();
    }

    @Override
    public List<Citizen> findPending() {
	return citizenRepository.findPending();
    }

    @Override
    @Transactional
    public long register(Citizen citizen) {
	// only required during creation
	if (!StringUtils.hasText(citizen.getPassword()))
	    throw new BusinessException(MISSING_PASSWORD, "Password is required");

	// TODO uncomment after tests
	// checkCitizenAttributes(citizen);

	// Update and creation of the citizen address
	addressService.createAddress(addressService.updateAddressGivenMunicipality(citizen.getAddress(),
		municipalityService.findByName(citizen.getAddress().getMunicipality())));

	// create user account
	String pass = PasswordUtil.encode(citizen.getPassword());
	UserAccount citizenAccount = new UserAccount(0L, 0L, citizen.getNationalRegisterNb(), UserType.CITIZEN,
		UserStatus.CREATED, pass, Collections.singletonList(Authority.USER.getAuthority()));
	long userAccountId = accountRepository.create(citizenAccount);
	return citizenRepository.create(citizen, userAccountId);
    }

    private void checkCitizenAttributes(Citizen citizen) throws BusinessException {
	if (!Pattern.matches(HermesRegex.ALLNAME.regex(), citizen.getFirstName())) {
	    throw new BusinessException(INVALID_FIRST_NAME, "The specified firstname is incorrect");
	}
	if (!Pattern.matches(HermesRegex.ALLNAME.regex(), citizen.getLastName())) {
	    throw new BusinessException(INVALID_LAST_NAME, "The specified lastName is incorrect");
	}
	if (!Pattern.matches(HermesRegex.MAIL.regex(), citizen.getMail())) {
	    throw new BusinessException(INVALID_EMAIL, "The specified mail is incorrect");
	}
	// impossible to satisfy
	// if (!Pattern.matches(HermesRegex.PHONE.regex(), citizen.getPhone())) {
	// throw new BusinessException("The specified phone is incorrect");
	// }
	if (citizen.getBirthdate().isAfter(LocalDate.now())) {
	    throw new BusinessException(INVALID_BIRTH_DATE, "The specified birth date is in the future");
	}

	// Address
	if (!Pattern.matches("Belgique|Belgium|Belgie", citizen.getAddress().getCountry())) {
	    throw new BusinessException(INVALID_COUNTRY, "The specified country is incorrect");
	}

	if (Objects.nonNull(citizen.getAddress().getState())
		&& !Pattern.matches(HermesRegex.COMMONNAME.regex(), citizen.getAddress().getState())) {
	    throw new BusinessException(INVALID_STATE, "The specified state is incorrect");
	}
	if (!Pattern.matches(HermesRegex.COMMONNAME.regex(), citizen.getAddress().getStreet())) {
	    throw new BusinessException(INVALID_STREET, "The specified street is incorrect");
	}
	if (!Pattern.matches(HermesRegex.INTEGER.regex(), Integer.toString(citizen.getAddress().getStreetNb()))) {
	    throw new BusinessException(INVALID_STREET_NUMBER, "The specified street number is incorrect");
	}
	if (!Pattern.matches(HermesRegex.ZIPCODE.regex(), Integer.toString(citizen.getAddress().getZipCode()))) {
	    throw new BusinessException(INVALID_ZIPCODE, "The specified zip code is incorrect");
	}
    }

    @Override
    public Boolean validateNRN(String citizenID) throws NRNNotValidException, NRNServiceAccessException {
	// Citizen citizen = citizenRepository.findById(citizenID);
	// String nRN = citizen.getNationalRegistreNb();
	NRNValidationModel nrnValidationModel = NRNValidationClient.getNRNValidationModel(citizenID);

	return nrnValidationModel.checkValidity();
    }

    @Override
    public UserAccount findAccount(String nationalRegistrationNb) throws BusinessException {
	return citizenRepository.findAccount(nationalRegistrationNb);
    }

    @Override
    public UserAccount findAccount(long citizenId) throws BusinessException {
	return citizenRepository.findAccount(citizenId);
    }

    @Override
    public void update(Long citizenId, Map<String, Object> updates) {
	// TODO Auto-generated method stub

    }
}
