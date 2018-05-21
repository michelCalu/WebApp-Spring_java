package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Municipality;

public interface AddressService {

    long createAddress (Address adress);

    Address findByCitizen (long citizenID);

    Address findByCompany (long entrepriseNb);

    Address findByMunicipality(long municipalityID);

    Address updateAddressGivenMunicipality(Address address, Municipality municipality);


}
