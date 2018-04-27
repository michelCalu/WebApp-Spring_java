package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    @Override
    public Company findByName(String firstName, String lastname) throws BusinessException {
        return null;
    }

    @Override
    public Company findByOwner(Long ownerCitizenId) throws BusinessException {
        return null;
    }


    @Override
    public Company findByEntrepriseNumber(String entrepriseNumber) throws BusinessException {
        return null;
    }

    @Override
    public Company findByVAT(String vatNb) throws BusinessException {
        return null;
    }
}
