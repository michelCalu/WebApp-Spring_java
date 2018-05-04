package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Municipality;

public interface MunicipalityService {

    Municipality findById(long municipalityID);

    Municipality findByAddress(Long addressID);

    Municipality findByZIPcode(Long zipcode);

    Municipality findByName(String name);

    List<Municipality> findAll();

    long activate(Municipality municipality);
}
