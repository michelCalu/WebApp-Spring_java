package be.unamur.hermes.business.model.NRNValidation;

import be.unamur.hermes.business.exception.NRNNotValidException;

public class NRNValidationModel {

    private String status;
    private Boolean belgian;
    private Boolean alive;
    private ExplanationIfInvalid explanationIfInvalid;

    public String getStatus() {
        return status;
    }

    public Boolean isBelgian() {
        return belgian;
    }

    public Boolean isAlive() {
        return alive;
    }

    public ExplanationIfInvalid getExplanationIfInvalid() {
        return explanationIfInvalid;
    }

    public Boolean checkValidity() throws NRNNotValidException{
        if(explanationIfInvalid != null)
            throw new NRNNotValidException(explanationIfInvalid.getDescription());
        else
            return true;
    }

}

