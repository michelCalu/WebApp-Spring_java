package be.unamur.hermes.common.enums;

public enum HermesRegex {
    ALLNAME     ("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð '-]+$"),
    COMMONNAME  ("^[a-zA-ZàáâäãèéêëîïòôöùûüçÈÉÊËÎÏÒÔÖÙÛÜ '-]+$"),
    PHONE       ("^([+]32)[1-9][0-9]{2}/([0-9]{2}[.]){2}[0-9]{2}$"),
    MAIL        ("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$"),
    INTEGER     ("^[1-9][0-9]*$"),
    ZIPCODE     ("^[1-9][0-9]{0,3}$");

    private final String regex;

    HermesRegex(String regex) {
        this.regex = regex;
    }

    public String regex() {
        return regex;
    }

}
