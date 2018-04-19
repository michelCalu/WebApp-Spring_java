package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNNotValidException;
import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNServiceAccessException;
import be.unamur.hermes.business.model.NRNValidation.NRNValidationModel;

public interface NRNService {

    NRNValidationModel getNRNValidationModel(String nRN) throws NRNServiceAccessException;

    boolean isNRNValid(String nRN) throws NRNServiceAccessException, NRNNotValidException;

}
