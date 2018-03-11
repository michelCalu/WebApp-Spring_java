package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Inhabitant;

public interface InhabitantRepository {

    Inhabitant findByName(String firstname, String lastname);

    void create(String firstname, String lastname);

}
