package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Municipality;

import java.util.List;

public interface MunicipalityService {

    Municipality findByAddress (Long addressID);

    Municipality findByZIPcode (Long zipcode);

    List<Municipality> findAll();

    void activate (Municipality municipality);


}
