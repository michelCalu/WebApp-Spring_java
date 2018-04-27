package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Address;

public interface AddressService {

    long createAddress (Address adress);

    Address findByCitizen (long citizenID);

    Address findByCompany (long entrepriseNb);

    Address findByMunicipality(long municipalityID);


}
