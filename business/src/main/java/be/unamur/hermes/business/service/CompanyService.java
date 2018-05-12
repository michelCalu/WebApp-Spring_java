package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Company;

import java.util.List;

public interface CompanyService {

    Company findByCompanyNb(String entrepriseNumber) throws BusinessException;

    List<Company> findAll();

    void register(Company company);

    List<Company> findPending(long municipalityID);
}
