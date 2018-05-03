package be.unamur.hermes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
	this.addressRepository = addressRepository;
    }

    @Override
    public long createAddress(Address address) {
	return addressRepository.create(address);
    }

    @Override
    public Address findByCitizen(long citizenID) {
	return null;
    }

    @Override
    public Address findByCompany(long entrepriseNb) {
	return null;
    }

    @Override
    public Address findByMunicipality(long municipalityID) {
	return null;
    }
}
