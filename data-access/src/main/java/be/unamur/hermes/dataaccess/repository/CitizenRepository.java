package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Citizen;

import java.util.List;

public interface CitizenRepository {

    Citizen findByName(String firstname, String lastname);

    Citizen findById(long citizenId);

    List<Citizen> findAll();

    void create(Citizen citizen);

    void activate(long citizenId);

}
