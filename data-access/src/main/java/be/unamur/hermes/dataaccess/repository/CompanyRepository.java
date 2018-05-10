package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Company;

import java.util.List;

public interface CompanyRepository {

    Company findByCompanyNb(String entrepriseNumber);

    List<Company> findAll();

    List<Company> findPending();

    long create(Company company);


}
