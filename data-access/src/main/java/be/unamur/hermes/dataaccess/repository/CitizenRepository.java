package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.UserAccount;

public interface CitizenRepository {

    Citizen findByName(String firstname, String lastname);

    Citizen findById(long citizenId);

    List<Citizen> findAll();

    List<Citizen> findPending();

    long create(Citizen citizen);

    void activate(long citizenId);

    UserAccount findAccount(String nationalRegistrationNb);

}
