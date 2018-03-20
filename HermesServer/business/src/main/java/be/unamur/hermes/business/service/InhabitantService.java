package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Inhabitant;

public interface InhabitantService {

    Inhabitant find(String firstName, String lastname);

    void register(String firstname, String lastname);
}
