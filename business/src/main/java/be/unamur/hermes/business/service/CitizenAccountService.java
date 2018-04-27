package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.CitizenAccount;

import java.util.List;

public interface CitizenAccountService {

    CitizenAccount findByName(String firstName, String lastname) throws BusinessException;

    CitizenAccount findById(Long citizenId) throws BusinessException;

    CitizenAccount findByNRN(String nrn) throws BusinessException;

    List<CitizenAccount> findAll();

    void activate (CitizenAccount citizenAccount) throws BusinessException;

    void lock (CitizenAccount citizenAccount) throws BusinessException;


}
