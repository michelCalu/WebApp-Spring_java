package be.unamur.hermes.common.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class HermesRegexTest {

    @Test
    public void test_regex_allName_positive() {
	assertTrue(Pattern.matches(HermesRegex.ALLNAME.regex(), "Jean-Philippe Ladrière d'Oswald"));
	assertTrue(Pattern.matches(HermesRegex.ALLNAME.regex(), "Björn Zélé d'Âtremonde"));
	assertTrue(Pattern.matches(HermesRegex.ALLNAME.regex(), "Huguette Ünùkí Yäqhì-Desmet"));
    }

    @Test
    public void test_regex_allName_negative() {
	assertFalse(Pattern.matches(HermesRegex.ALLNAME.regex(), "insert into t_requests values(1,2,3)"));
	assertFalse(Pattern.matches(HermesRegex.ALLNAME.regex(), "<!DOCTYPE html>"));
	assertFalse(Pattern.matches(HermesRegex.ALLNAME.regex(), "public static void main(String[] args)"));
    }

    @Test
    public void test_regex_phoneNumber_positive() {
	assertTrue(Pattern.matches(HermesRegex.PHONE.regex(), "+32472245565"));
	assertTrue(Pattern.matches(HermesRegex.PHONE.regex(), "0472/245565"));
	assertTrue(Pattern.matches(HermesRegex.PHONE.regex(), "0472/24.55.65"));
    }

    @Test
    public void test_regex_phoneNumber_negative() {
	assertFalse(Pattern.matches(HermesRegex.PHONE.regex(), "+324722"));
	assertFalse(Pattern.matches(HermesRegex.PHONE.regex(), "0472-24-55-65"));
	assertFalse(Pattern.matches(HermesRegex.PHONE.regex(), "04722455650472245565"));
    }

    @Test
    public void test_regex_mail_positive() {
	assertTrue(Pattern.matches(HermesRegex.MAIL.regex(), "thomas.elskens@hotmail.com"));
	assertTrue(Pattern.matches(HermesRegex.MAIL.regex(), "info@commune.be"));
	assertTrue(Pattern.matches(HermesRegex.MAIL.regex(), "123@blabla.eu"));
    }

    @Test
    public void test_regex_mail_negative() {
	assertFalse(Pattern.matches(HermesRegex.MAIL.regex(), "thomas.elskens.hotmail.com"));
	assertFalse(Pattern.matches(HermesRegex.MAIL.regex(), "info@commune"));
	assertFalse(Pattern.matches(HermesRegex.MAIL.regex(), "123@.eu"));
    }

    @Test
    public void test_regex_zipCode_positive() {
	assertTrue(Pattern.matches(HermesRegex.ZIPCODE.regex(), "2650"));
	assertTrue(Pattern.matches(HermesRegex.ZIPCODE.regex(), "1000"));
	assertTrue(Pattern.matches(HermesRegex.ZIPCODE.regex(), "5896"));
    }

    @Test
    public void test_regex_zipCode_negative() {
	assertFalse(Pattern.matches(HermesRegex.ZIPCODE.regex(), "12"));
	assertFalse(Pattern.matches(HermesRegex.ZIPCODE.regex(), "123123123"));
	assertFalse(Pattern.matches(HermesRegex.ZIPCODE.regex(), "zipCode"));
    }
}
