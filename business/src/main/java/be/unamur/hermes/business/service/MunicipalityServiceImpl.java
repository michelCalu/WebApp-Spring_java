package be.unamur.hermes.business.service;

import java.util.List;

import be.unamur.hermes.business.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.repository.MunicipalityRepository;

import static be.unamur.hermes.common.exception.Errors.FAILURE_DATABASE_RETRIEVAL;

@Service
public class MunicipalityServiceImpl implements MunicipalityService {

    private MunicipalityRepository municipalityRepository;
    private AddressService addressService;

    @Autowired
    public MunicipalityServiceImpl(MunicipalityRepository municipalityRepository,
                                   AddressService addressService) {
	this.municipalityRepository = municipalityRepository;
	this.addressService = addressService;
    }

    @Override
    public Municipality findByAddress(Long addressID) {
	return municipalityRepository.findByAddress(addressID);
    }

    @Override
    public Municipality findByZIPcode(Long zipcode) {
	return municipalityRepository.findByZipCode(zipcode);
    }

    @Override
    public List<Municipality> findAll() {
	return municipalityRepository.findAll();
    }

    @Override
    public long activate(Municipality municipality) {
	    addressService.createAddress(municipality.getAddress());
        return municipalityRepository.create(municipality);
    }

    @Override
    public Municipality findById(long municipalityID) {
	return municipalityRepository.findById(municipalityID);
    }

    @Override
    public Municipality findByName(String name) {
        Municipality municipality = municipalityRepository.findByName(name);
        if (municipality == null)
            throw new BusinessException(FAILURE_DATABASE_RETRIEVAL,
                    "The municipality is not recognized by the system.");
        return municipality;
    }
}
