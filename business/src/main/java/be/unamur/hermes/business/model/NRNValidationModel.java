package be.unamur.hermes.business.model;

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

    public Boolean checkValidity() throws NRNNotValidException{
        if(explanationIfInvalid != null)
            throw new NRNNotValidException(explanationIfInvalid.description);
        else
            return true;
    }

    private class ExplanationIfInvalid{

        private String code;
        private String description;

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}

