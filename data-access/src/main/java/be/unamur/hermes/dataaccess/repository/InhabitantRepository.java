package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Inhabitant;

import java.util.List;

public interface InhabitantRepository {

    Inhabitant findByName(String firstname, String lastname);

    Inhabitant findById(Long inhabitantId);

    List<Inhabitant> findAll();

    void create(String firstname, String lastname);

}
