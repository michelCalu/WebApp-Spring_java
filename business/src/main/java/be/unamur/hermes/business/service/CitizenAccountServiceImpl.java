package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.CitizenAccount;
import be.unamur.hermes.dataaccess.repository.CitizenAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenAccountServiceImpl implements CitizenAccountService{

    private CitizenAccountRepository citizenAccountRepository;

    @Autowired
    public CitizenAccountServiceImpl(CitizenAccountRepository citizenAccountRepository) {
        this.citizenAccountRepository = citizenAccountRepository;
    }

    @Override
    public CitizenAccount findByName(String firstName, String lastname) throws BusinessException {
        return null;
    }

    @Override
    public CitizenAccount findById(Long citizenId) throws BusinessException {
        return null;
    }

    @Override
    public CitizenAccount findByNRN(String nrn) throws BusinessException {
        return null;
    }

    @Override
    public List<CitizenAccount> findAll() {
        return null;
    }

    @Override
    public void activate(CitizenAccount citizenAccount) {

    }

    @Override
    public void lock(CitizenAccount citizenAccount) throws BusinessException {

    }
}
