package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Claim;

public interface ClaimRepository {

    List<Claim> findByCitizen(long citizenId);

    Claim findById(long id);

    void create(long type, long citizenId);

}
