package be.unamur.hermes.common.valuestypes;
import be.unamur.hermes.common.exception.InvalidNRNFormatException;

/**
 * this class creates a proper NRN respecting format:
 * [YYMMDDABCDE] with YYMMDD as birthdate and ABCDE a 5-digit number
 */

public class RegistryNumber {

    private final String value;

    public static RegistryNumber create(String numberString) throws InvalidNRNFormatException {

        if(numberString.length()!=11) throw new InvalidNRNFormatException("invalid NRN length");
        if(!numberString.matches("[0-9]+")) throw new InvalidNRNFormatException("invalid chars in NRN");

        String[] nrnArray = numberString.split("");
        StringBuilder strMonth = new StringBuilder();
        StringBuilder strDay = new StringBuilder();
        strMonth.append(nrnArray[2]);
        strMonth.append(nrnArray[3]);
        strDay.append(nrnArray[4]);
        strDay.append(nrnArray[5]);
        int monthInt = Integer.parseInt(strMonth.toString());
        int dayInt = Integer.parseInt(strDay.toString());

        if(monthInt<0 || monthInt > 12 || dayInt<0 || dayInt > 31) {
            throw new InvalidNRNFormatException("invalid date format in NRN");
        }

        return new RegistryNumber(numberString);
    }

    private RegistryNumber(String value) {
	super();
	this.value = value;
    }

}
