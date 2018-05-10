package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.UserAccount;

import java.util.List;

public interface CompanyService {

    Company findByEntrepriseNb(String entrepriseNumber) throws BusinessException;

    UserAccount findAccount(String entrepriseNb) throws BusinessException;

    List<Company> findAll();

    List<Company> findPending();

    String register(Company company);

}
