package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Municipality;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipalityRepositoryImpl implements MunicipalityRepository {

    @Override
    public long create() {
        return 0;
    }

    @Override
    public Municipality findByName(String name) {
        return null;
    }

    @Override
    public Municipality findById(long id) {
        return null;
    }

}
