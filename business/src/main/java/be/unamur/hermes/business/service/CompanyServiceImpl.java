package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.Authority;
import be.unamur.hermes.common.enums.UserStatus;
import be.unamur.hermes.common.enums.UserType;
import be.unamur.hermes.common.util.PasswordUtil;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.dataaccess.repository.CompanyRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

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
    public Company findByEntrepriseNb(String entrepriseNumber) throws BusinessException {
        try {
            return companyRepository.finByEntrepriseNb(entrepriseNumber);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Company not found !");
        }
    }

    @Override
    public UserAccount findAccount(String entrepriseNb) throws BusinessException {
        return null;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public List<Company> findPending() {
        return companyRepository.findPending();
    }


    @Override
    @Transactional
    public String register(Company company) {
        if (!StringUtils.hasText(company.getPassword()))
            throw new BusinessException("Password is required");
        String municipalityName = company.getAddress().getMunicipality();
        if(municipalityRepository.findByName(municipalityName) == null)
            throw new BusinessException("The company's municipality isn't recognized by the system.");
        // create user account
        String pass = PasswordUtil.encode(company.getPassword());
        UserAccount companyAccount = new UserAccount(0L, 0L, company.getCompanyNb(), UserType.COMPANY,
                UserStatus.CREATED, pass, UserAccount.prepareAuthorities(Authority.USER.getAuthority()));
        long userAccountId = accountRepository.create(companyAccount);
        return companyRepository.create(company, userAccountId);
    }
}
