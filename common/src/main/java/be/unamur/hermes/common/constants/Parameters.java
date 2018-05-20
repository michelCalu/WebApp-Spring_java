package be.unamur.hermes.common.constants;

/**
 * Known parameters, which have to be referenced (e.g. for document generation).
 * 
 * @author Thomas_Elskens
 *
 */
public interface Parameters {

    /** Is the request active or not ? Type = boolean **/
    String ACTIVATED = "activated";

    /**
     * How long the parking card remains valid ? Type = integer (number of weeks)
     **/
    String PARKING_CARD_PERIOD_VALIDITY = "parkingCard.periodValidity";

    /**
     * Which term is set for the payment of the parking card ? Type = integer
     * (number of weeks)
     **/
    String PARKING_CARD_TERM_PAYMENT = "parkingCard.termPayment";

    /**
     * Fee of the parking card. Type = double (euro)
     */
    String PARKING_CARD_FEE = "parkingCard.fee";

}
