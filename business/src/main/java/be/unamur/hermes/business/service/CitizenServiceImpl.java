package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.*;
import be.unamur.hermes.business.exception.NRNNotValidException;
import be.unamur.hermes.business.exception.NRNServiceAccessException;
import be.unamur.hermes.business.io.NRNValidation;
import be.unamur.hermes.business.model.NRNValidation.NRNValidationModel;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CitizenServiceImpl implements CitizenService {

    private CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository){
        this.citizenRepository = citizenRepository;
    }

    @Override
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
    public List<Citizen> findPending() {
        return citizenRepository.findPending();
    }

    @Override
    @Transactional
    public long register(Citizen citizen) {
        checkCitizenAttributes(citizen);
        return citizenRepository.create(citizen);
    }

    @Override
    @Transactional
    public Citizen activate(Citizen citizen) throws BusinessException {
        if(citizen.isActivated()) {
            throw new BusinessException("This citizen is already activated !");
        } else {
            citizenRepository.activate(citizen.getId());
            return citizenRepository.findById(citizen.getId());
        }
    }

    private void checkCitizenAttributes(Citizen citizen) throws BusinessException {
        if (!Pattern.matches(
                HermesRegex.ALLNAME.regex(),
                citizen.getFirstName())) {
            throw new BusinessException("The specified firstname is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.ALLNAME.regex(),
                citizen.getLastName())) {
            throw new BusinessException("The specified lastName is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.MAIL.regex(),
                citizen.getMail())) {
            throw new BusinessException("The specified mail is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.PHONE.regex(),
                citizen.getPhone())) {
            throw new BusinessException("The specified phone is incorrect");
        }
        if (citizen.getBirthdate().isAfter(LocalDate.now())) {
            throw new BusinessException("The specified birth date is in the future");
        }

        // Address
        if (!Pattern.matches(
                "Belgique|Belgium|Belgie",
                citizen.getAddress().getCountry())) {
            throw new BusinessException("The specified country is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.COMMONNAME.regex(),
                citizen.getAddress().getState())) {
            throw new BusinessException("The specified state is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.COMMONNAME.regex(),
                citizen.getAddress().getStreet())) {
            throw new BusinessException("The specified street is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.INTEGER.regex(),
                Integer.toString(citizen.getAddress().getStreetNb()))) {
            throw new BusinessException("The specified street number is incorrect");
        }
        if (!Pattern.matches(
                HermesRegex.ZIPCODE.regex(),
                Integer.toString(citizen.getAddress().getZipCode()))) {
            throw new BusinessException("The specified zip code is incorrect");
        }
    }

    @Override
    public Boolean validateNRN(String citizenID) throws NRNNotValidException, NRNServiceAccessException {
        //Citizen citizen = citizenRepository.findById(citizenID);
        //String nRN = citizen.getNationalRegistreNb();
        NRNValidationModel nrnValidationModel = new NRNValidation().validate(citizenID);

        return nrnValidationModel.checkValidity();
    }
}
