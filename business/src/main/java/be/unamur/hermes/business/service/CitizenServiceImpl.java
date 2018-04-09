package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitizenServiceImpl implements CitizenService {

    private CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository){
        this.citizenRepository = citizenRepository;
    }

    @Override
    @Transactional
    public Citizen findByName(String firstName, String lastname) throws BusinessException {
        try {
            return citizenRepository.findByName(firstName, lastname);
        } catch(EmptyResultDataAccessException e){
            throw new BusinessException("Citizen not found !");
        }
    }

    @Override
    public Citizen findById(Long citizenId) throws BusinessException {
        try {
            return citizenRepository.findById(citizenId);
        } catch(EmptyResultDataAccessException e){
            throw new BusinessException("Citizen not found !");
        }
    }

    @Override
    public List<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    @Override
    @Transactional
    public void register(Citizen citizen) {
        citizenRepository.create(citizen);
    }

    @Override
    public Citizen activate(Citizen citizen) throws BusinessException {
        if(citizen.isActivated()) {
            throw new BusinessException("This citizen is already activated !");
        } else {
            citizenRepository.activate(citizen.getId());
            return citizenRepository.findById(citizen.getId());
        }
    }
}
