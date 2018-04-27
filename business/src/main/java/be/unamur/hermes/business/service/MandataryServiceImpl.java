package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Company;
import be.unamur.hermes.dataaccess.entity.Mandatary;
import be.unamur.hermes.dataaccess.entity.MandataryRole;
import be.unamur.hermes.dataaccess.repository.MandataryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MandataryServiceImpl implements MandataryService {

    private MandataryRepository mandataryRepository;

    @Autowired
    public MandataryServiceImpl(MandataryRepository mandataryRepository) {
        this.mandataryRepository = mandataryRepository;
    }

    @Override
    public Mandatary findByCitizen(Long CitizenId) {
        return null;
    }

    @Override
    public List<Mandatary> findByCompany(String EntrepriseNb) {
        return null;
    }

    @Override
    public List<Mandatary> findAll() {
        return null;
    }

    @Override
    public Long register(Mandatary mandatary, Company company, MandataryRole mandataryRole) {
        return null;
    }
}
