package be.unamur.hermes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
	this.companyRepository = companyRepository;
    }

    @Override
    public Company findByCompanyNumber(String entrepriseNumber) throws BusinessException {
	return companyRepository.findById(entrepriseNumber);
    }

    @Override
    public Company findByVAT(String vatNb) throws BusinessException {
	return companyRepository.findByVAT(vatNb);
    }

    @Override
    public String create(Company company) {
	return companyRepository.create(company);
    }
}
