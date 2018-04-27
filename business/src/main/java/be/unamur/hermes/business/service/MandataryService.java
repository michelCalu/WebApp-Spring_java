package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Mandatary;
import be.unamur.hermes.dataaccess.entity.MandataryRole;

import java.util.List;

public interface MandataryService {

    Mandatary findByCitizen (Long CitizenId);

    List<Mandatary> findByCompany (String EntrepriseNb);

    List<Mandatary> findAll ();

    Long register (Mandatary mandatary, Company company, MandataryRole mandataryRole);


}
