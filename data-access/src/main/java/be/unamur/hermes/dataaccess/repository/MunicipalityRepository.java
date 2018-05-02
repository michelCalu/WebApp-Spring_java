package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Municipality;

public interface MunicipalityRepository {

    public long create(Municipality municipality);

    public Municipality findByName(String name);

    public Municipality findById(long id);

}
