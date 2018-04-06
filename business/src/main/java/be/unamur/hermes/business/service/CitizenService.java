package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Citizen;

import java.util.List;

public interface CitizenService {

    Citizen findByName(String firstName, String lastname);

    Citizen findById(Long citizenId);

    List<Citizen> findAll();

    void register(String firstname, String lastname);
}
