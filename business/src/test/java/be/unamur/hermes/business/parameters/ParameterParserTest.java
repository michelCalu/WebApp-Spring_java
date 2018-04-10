package be.unamur.hermes.business.parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Map;

import org.junit.Test;
import org.w3c.dom.Document;

import be.unamur.hermes.common.enums.ClaimId;
import be.unamur.hermes.common.util.XMLUtil;

public class ParameterParserTest {

    private static final String TEST_FILE_NAME = "parameters-test.xml";
    private static final String KEY_GEMBLOUX = "Gembloux";
    private static final String KEY_NAMUR = "Namur";

    @Test
    public void test_municipalities() {
	ParameterParser parser = new ParameterParser();
	try (InputStream is = ParameterParser.class.getClassLoader().getResourceAsStream(TEST_FILE_NAME)) {
	    Map<String, ClaimParameterSet> municipalities = parser.parse(is);
	    assertNotNull(municipalities);
	    assertEquals(2, municipalities.size());
	} catch (Exception e) {
	    fail(e.getMessage());
	}
    }

    @Test
    public void test_parameter_activated() {
	ParameterParser parser = new ParameterParser();
	try (InputStream is = ParameterParser.class.getClassLoader().getResourceAsStream(TEST_FILE_NAME)) {
	    Map<String, ClaimParameterSet> municipalities = parser.parse(is);
	    assertNotNull(municipalities);
	    ClaimParameterSet parameters = municipalities.get(KEY_NAMUR);
	    boolean nationalityClaimActivated = parameters.isActivated(ClaimId.NATIONALITY_CERTIFICATE);
	    assertFalse("Nationality Certificate of Namur is activated", nationalityClaimActivated);
	    boolean parkingPermActivated = parameters.isActivated(ClaimId.PARKING_PERMISSION);
	    assertTrue("Nationality Certificate of Namur is not activated", parkingPermActivated);
	} catch (Exception e) {
	    e.printStackTrace();
	    fail(e.getMessage());
	}
    }

    @Test
    public void test_nested_parameters() {
	ParameterParser parser = new ParameterParser();
	try (InputStream is = ParameterParser.class.getClassLoader().getResourceAsStream(TEST_FILE_NAME)) {
	    Document doc = XMLUtil.toDocument(is);
	    ClaimParameterSet cps = parser.parseMunicipality(doc, KEY_GEMBLOUX);
	    assertNotNull("Expected Gembloux", cps);
	    Object nestedParameter1 = cps.getParameter(ClaimId.PARKING_PERMISSION, "nestingParam/nestedParam1");
	    assertNotNull("Expected 'nestingParam1/nestedParam1'", nestedParameter1);
	    assertEquals("nestedValue1", String.valueOf(nestedParameter1));
	} catch (Exception e) {
	    fail(e.getMessage());
	}
    }

}
