package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.PeopleBis;
import be.unamur.hermes.dataaccess.repository.PeopleBisRepository;
import be.unamur.hermes.dataaccess.repository.PeopleBisRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleBisServiceImpl implements PeopleBisService{

    private PeopleBisRepository peopleBisRepository;

    @Autowired
    PeopleBisServiceImpl(PeopleBisRepository peopleBisRepository){
        this.peopleBisRepository = peopleBisRepository;
    }

    @Override
    public List<PeopleBis> findAll() {
        return peopleBisRepository.findAll();
    }
}
