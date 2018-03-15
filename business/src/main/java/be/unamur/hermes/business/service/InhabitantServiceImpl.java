package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Inhabitant;
import be.unamur.hermes.dataaccess.repository.InhabitantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InhabitantServiceImpl implements InhabitantService {

    private InhabitantRepository inhabitantRepository;

    @Autowired
    public InhabitantServiceImpl(InhabitantRepository inhabitantRepository){
        this.inhabitantRepository = inhabitantRepository;
    }

    @Override
    @Transactional
    public Inhabitant find(String firstName, String lastname) {
        return inhabitantRepository.findByName(firstName,lastname);
    }

    @Override
    @Transactional
    public void register(String firstname, String lastname) {
        inhabitantRepository.create(firstname,lastname);
    }
}
