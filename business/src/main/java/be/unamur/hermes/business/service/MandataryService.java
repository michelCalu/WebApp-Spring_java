package be.unamur.hermes.business.service;

import be.unamur.hermes.common.enums.MandataryRole;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Mandatary;

import java.util.List;
import java.util.Optional;

public interface MandataryService {

    List<Mandatary> findByCitizen(Long citizenId, Optional<String> companyStatus);

    List<Mandatary> findByCompany(String entrepriseNb);

    List<Mandatary> findByCompany(String entrepriseNb, String role);

    Mandatary findById(long id);

    List<Mandatary> findAll();

    Long create(Citizen citizen, Company company, MandataryRole role);

}
