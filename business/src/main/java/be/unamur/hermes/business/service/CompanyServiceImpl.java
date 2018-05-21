package be.unamur.hermes.business.service;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import be.unamur.hermes.dataaccess.entity.Municipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.HermesRegex;
import be.unamur.hermes.common.exception.Errors;
import be.unamur.hermes.dataaccess.dto.UpdateCompanyAccount;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.repository.CompanyRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;

@Service
public class CompanyServiceImpl implements CompanyService, Errors {

    private CompanyRepository companyRepository;
    private final AddressService addressService;
    private final MunicipalityService municipalityService;
    private final UserAccountRepository accountRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AddressService addressService,
	    UserAccountRepository userAccountRepository, MunicipalityService municipalityService) {
	this.companyRepository = companyRepository;
	this.addressService = addressService;
	this.accountRepository = userAccountRepository;
	this.municipalityService = municipalityService;
    }

    @Override
    public Company findByCompanyNb(String entrepriseNumber) throws BusinessException {
	return companyRepository.findByCompanyNb(entrepriseNumber);
    }

    @Override
    public List<Company> findAll() {
	return companyRepository.findAll();
    }

    @Override
    @Transactional
    public void register(Company company) {
	// checkCompanyAttributes(company);

        // Update and creation of the company's address
		addressService.createAddress(
		    addressService.updateAddressGivenMunicipality(
		        company.getAddress(),
                    municipalityService.findByName(company.getAddress().getMunicipality())
            ));

		try {
	    	companyRepository.create(company);
		} catch (SQLException ex) {
	    	throw new BusinessException(FAILURE_DATABASE_RETRIEVAL, ex.getMessage(), ex);
		}
    }

    @Override
    public List<Company> findPending(long municipalityID) {
	return companyRepository.findPending(municipalityID);
    }

    @Override
    public void activate(String companyNb, UpdateCompanyAccount updates) {
	companyRepository.activate(companyNb, updates);
    }

    private void checkCompanyAttributes(Company company) throws BusinessException {
	if (!Pattern.matches(HermesRegex.VATNB.regex(), company.getCompanyNb())) {
	    throw new BusinessException(INVALID_VAT_NUMBER, "The specified VAT number is incorrect");
	}
	if (!Pattern.matches(HermesRegex.INTEGER.regex(), company.getCompanyNb())) {
	    throw new BusinessException(INVALID_COMPANY_NUMBER, "The specified lastName is incorrect");
	}
	if (!Pattern.matches(HermesRegex.ALLNAME.regex(), company.getLegalForm())) {
	    throw new BusinessException(INVALID_LEGAL_FORM, "The specified legal form is incorrect");
	}
    }
}
