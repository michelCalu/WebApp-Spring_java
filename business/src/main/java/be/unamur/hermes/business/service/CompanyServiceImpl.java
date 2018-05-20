package be.unamur.hermes.business.service;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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
    private MunicipalityRepository municipalityRepository;
    private final UserAccountRepository accountRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, MunicipalityRepository municipalityRepository,
	    UserAccountRepository userAccountRepository) {
	this.companyRepository = companyRepository;
	this.municipalityRepository = municipalityRepository;
	this.accountRepository = userAccountRepository;
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
	String municipalityName = company.getAddress().getMunicipality();
	if (municipalityRepository.findByName(municipalityName) == null)
	    throw new BusinessException(INVALID_MUNICIPALITY,
		    "The company's municipality is not recognized by the system.");
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
