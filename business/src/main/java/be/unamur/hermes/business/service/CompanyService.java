package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;

import java.util.List;

public interface CompanyService {

    Company findByEntrepriseNb(String entrepriseNumber) throws BusinessException;

    List<Company> findAll();

    List<Company> findPending();

    long register(Company company, Citizen citizen);

}
