package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.HermesRegex;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.repository.CompanyRepository;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import be.unamur.hermes.dataaccess.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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
    public Company findByCompanyNb(String entrepriseNumber) throws BusinessException {
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
    @Transactional
    public void register(Company company)  {
        //checkCompanyAttributes(company);
        String municipalityName = company.getAddress().getMunicipality();
        if(municipalityRepository.findByName(municipalityName) == null)
            throw new BusinessException("The company's municipality isn't recognized by the system.");
        try{
            companyRepository.create(company);
        }catch(SQLException e){
            throw new BusinessException("error creating company");
        }

    }

    @Override
    public List<Company> findPending(long municipalityID) {
        return companyRepository.findPending(municipalityID);
    }

    private void checkCompanyAttributes(Company company) throws BusinessException{
        if (!Pattern.matches(HermesRegex.VATNB.regex(), company.getCompanyNb())) {
            throw new BusinessException("The specified VAT number is incorrect");
        }
        if (!Pattern.matches(HermesRegex.INTEGER.regex(), company.getCompanyNb())) {
            throw new BusinessException("The specified lastName is incorrect");
        }
        if (!Pattern.matches(HermesRegex.ALLNAME.regex(), company.getLegalForm())) {
            throw new BusinessException("The specified legal form is incorrect");
        }
    }
}
