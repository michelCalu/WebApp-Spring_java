package be.unamur.hermes.common.valuestypes;

/**
 * This class holds a valid instance of a Registry Number.
 *
 */
public class RegistryNumber {

    private final String value;

    public static RegistryNumber create(String numberString) {
	// TODO some validation on wellformedness
	return new RegistryNumber(numberString);
    }

    private RegistryNumber(String value) {
	super();
	this.value = value;
    }
}
