package be.unamur.hermes.business.service;

import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNNotValidException;
import be.unamur.hermes.business.exception.NRNNotValidExceptions.NRNServiceAccessException;
import be.unamur.hermes.business.io.NRNValidationClient;
import be.unamur.hermes.business.model.NRNValidation.NRNValidationModel;
import org.springframework.stereotype.Service;

@Service
public class NRNServiceImpl implements NRNService {
    @Override
    public NRNValidationModel getNRNValidationModel(String nRN) throws NRNServiceAccessException {
        return NRNValidationClient.getNRNValidationModel(nRN);
    }

    @Override
    public boolean isNRNValid(String nRN) throws NRNServiceAccessException, NRNNotValidException {
        NRNValidationModel nRNValidationModel = getNRNValidationModel(nRN);
        return nRNValidationModel.checkValidity();
    }
}
