package be.unamur.hermes.business.service;

import java.util.List;
import java.util.Optional;

import be.unamur.hermes.dataaccess.entity.Mandatary;

public interface MandataryService {

    List<Mandatary> findByCitizen(Long citizenId, Optional<String> companyStatus);

    List<Mandatary> findByCompany(String entrepriseNb);

    List<Mandatary> findByCompany(String entrepriseNb, String role);

    Mandatary findById(long id);

    List<Mandatary> findAll();

    Long register(Mandatary mandatary);

}
