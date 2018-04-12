package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Town;

public interface TownRepository {

    public long create();

    public Town findByName(String name);

    public Town findById(long id);

}
