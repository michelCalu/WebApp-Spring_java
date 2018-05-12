package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyRepository {

    Company findByCompanyNb(String entrepriseNumber);

    List<Company> findAll();

    void create(Company company) throws SQLException;

    List<Company> findPending(long municipalityID);
}
