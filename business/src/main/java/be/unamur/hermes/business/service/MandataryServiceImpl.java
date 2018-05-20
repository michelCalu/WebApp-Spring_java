package be.unamur.hermes.business.service;

import be.unamur.hermes.common.enums.MandataryRole;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Mandatary;
import be.unamur.hermes.dataaccess.repository.MandataryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MandataryServiceImpl implements MandataryService {

    private MandataryRepository mandataryRepository;

    @Autowired
    public MandataryServiceImpl(MandataryRepository mandataryRepository) {
	this.mandataryRepository = mandataryRepository;
    }

    @Override
    public List<Mandatary> findByCompany(String EntrepriseNb) {
	return mandataryRepository.getMandatariesByCompanyNb(EntrepriseNb);
    }

    @Override
    public List<Mandatary> findByCompany(String EntrepriseNb, String role) {
	return mandataryRepository.getMandataries(EntrepriseNb, role);
    }

    @Override
    public List<Mandatary> findAll() {
	return mandataryRepository.findAll();
    }

    @Override
    public Long create(Citizen citizen, Company company, MandataryRole role) {
        Mandatary mandatary = new Mandatary(citizen, company, role);
        return mandataryRepository.create(mandatary);
    }

    @Override
    public List<Mandatary> findByCitizen(Long citizenId, Optional<String> companyStatus) {
	return mandataryRepository.findByCitizenId(citizenId, companyStatus);
    }

    @Override
    public Mandatary findById(long id) {
	return mandataryRepository.findById(id);
    }
}
