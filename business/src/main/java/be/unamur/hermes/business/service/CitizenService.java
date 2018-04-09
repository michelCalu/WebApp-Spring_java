package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Citizen;

import java.util.List;

public interface CitizenService {

    Citizen findByName(String firstName, String lastname) throws BusinessException;

    Citizen findById(Long citizenId) throws BusinessException;

    List<Citizen> findAll();

    void register(Citizen citizen);

    Citizen activate(Citizen citizen) throws BusinessException;
}
