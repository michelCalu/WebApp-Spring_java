package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Mandatary;

public interface MandataryService {

    List<Mandatary> findByCitizen(Long citizenId);

    List<Mandatary> findByCompany(String entrepriseNb);

    List<Mandatary> findByCompany(String entrepriseNb, String role);

    Mandatary findById(long id);

    List<Mandatary> findAll();

    Long register(Mandatary mandatary);

}
