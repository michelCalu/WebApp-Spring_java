package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Mandatary;

public interface MandataryRepository {

    public List<Mandatary> getMandatariesByCompanyNb(String companyNb);

    public List<Mandatary> getMandataries(String companyNb, String role);

    public Mandatary findById(long id);

    public List<Mandatary> findByCitizenId(long citizenId);

    public List<Mandatary> findAll();

    public long create(Mandatary mandatary);

}
