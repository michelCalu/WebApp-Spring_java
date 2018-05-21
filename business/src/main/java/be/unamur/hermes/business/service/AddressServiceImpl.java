package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Municipality;
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
        // We set the address id so that object using this address can refer to it.
        Long addressID = addressRepository.create(address);
        address.setId(addressID);
	    return addressID;
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

    @Override
    public Address updateAddressGivenMunicipality(Address address, Municipality municipality) {
        address.setZipCode(municipality.getAddress().getZipCode());
        address.setState(municipality.getAddress().getState());
        address.setCountry(municipality.getAddress().getCountry());
        return address;
    }

}
