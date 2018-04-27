package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityServiceImpl implements MunicipalityService{

    private MunicipalityRepository municipalityRepository;

    @Autowired
    public MunicipalityServiceImpl(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    @Override
    public Municipality findByAddress(Long addressID) {
        return null;
    }

    @Override
    public Municipality findByZIPcode(Long zipcode) {
        return null;
    }

    @Override
    public List<Municipality> findAll() {
        return null;
    }

    @Override
    public void activate(Municipality municipality) {

    }
}
