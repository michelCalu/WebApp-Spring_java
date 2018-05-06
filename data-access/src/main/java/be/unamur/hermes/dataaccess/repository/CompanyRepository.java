package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Company;

public interface CompanyRepository {

    public String create(Company company);

    public Company findById(String companyNb);

    public Company findByVAT(String vat);
}
