package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.repository.CompanyRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return companyRepository.findByCompanyNb(entrepriseNumber);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Company not found !");
        }
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
    public long register(Company company, Citizen citizen) {
        //checkCompanyAttributes(company);
        String municipalityName = company.getAddress().getMunicipality();
        if(municipalityRepository.findByName(municipalityName) == null)
            throw new BusinessException("The company's municipality isn't recognized by the system.");

        long companyId= companyRepository.create(company);
        //TODO create mandatary

        return companyId;

    }

    private void checkCompanyAttributes(Company company) throws BusinessException{
       // TODO
    }
}
