package be.unamur.hermes.business.service;

import java.util.List;
import java.util.Map;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNNotValidException;
import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNServiceAccessException;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.UserAccount;

public interface CitizenService {

    Citizen findByName(String firstName, String lastname) throws BusinessException;

    Citizen findById(Long citizenId) throws BusinessException;

    UserAccount findAccount(String nationalRegistrationNb) throws BusinessException;

    List<Citizen> findAll();

    List<Citizen> findPending();

    long register(Citizen citizen) throws BusinessException;

    Boolean validateNRN(String citizenID) throws NRNNotValidException, NRNServiceAccessException;

    void update(Long citizenId, Map<String, Object> updates);
}
