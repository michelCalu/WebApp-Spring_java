package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Inhabitant;
import be.unamur.hermes.dataaccess.repository.InhabitantRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InhabitantServiceImpl implements InhabitantService {

    private InhabitantRepository inhabitantRepository;

    @Autowired
    public InhabitantServiceImpl(InhabitantRepository inhabitantRepository){
        this.inhabitantRepository = inhabitantRepository;
    }

    @Override
    public Inhabitant find(String firstName, String lastname) {
        return inhabitantRepository.findByName(firstName,lastname);
    }

    @Override
    public void register(String firstname, String lastname) {
        inhabitantRepository.create(firstname,lastname);
    }
}
