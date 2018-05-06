package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Company;

public interface CompanyService {

    Company findByCompanyNumber(String entrepriseNumber) throws BusinessException;

    Company findByVAT(String vatNb) throws BusinessException;

    String create(Company company);

}
