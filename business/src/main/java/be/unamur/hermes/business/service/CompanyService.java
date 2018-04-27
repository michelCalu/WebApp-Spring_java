package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Company;

public interface CompanyService {

    Company findByName(String firstName, String lastname) throws BusinessException;

    Company findByOwner (Long ownerCitizenId) throws BusinessException;

    Company findByEntrepriseNumber(String entrepriseNumber) throws BusinessException;

    Company findByVAT(String vatNb) throws BusinessException;



}
