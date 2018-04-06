package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Inhabitant;

import java.util.List;

public interface InhabitantService {

    Inhabitant findByName(String firstName, String lastname);

    Inhabitant findById(Long inhabitantId);

    List<Inhabitant> findAll();

    void register(String firstname, String lastname);
}
