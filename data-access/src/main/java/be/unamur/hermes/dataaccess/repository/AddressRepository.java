package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Address;

public interface AddressRepository {

    Address findById(Long addressID);

    long create(Address address);

}
