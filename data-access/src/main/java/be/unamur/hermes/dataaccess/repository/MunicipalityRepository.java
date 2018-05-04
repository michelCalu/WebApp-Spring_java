package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Municipality;

public interface MunicipalityRepository {

    public long create(Municipality municipality);

    public Municipality findByName(String name);

    public Municipality findById(long id);

    public Municipality findByAddress(long addressId);

    public Municipality findByZipCode(long zipCode);

    public List<Municipality> findAll();

}
