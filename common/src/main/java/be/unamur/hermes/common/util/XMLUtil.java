package be.unamur.hermes.common.util;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public interface XMLUtil {

    static Document toDocument(InputStream is) throws Exception {
	return getBuilder().parse(is);
    }

    static Document toDocument(String xml) throws Exception {
	return getBuilder().parse(new InputSource(new StringReader(xml)));
    }

    static DocumentBuilder getBuilder() {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try {
	    return factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    LoggerFactory.getLogger(XMLUtil.class).error(e.getMessage(), e);
	    return null;
	}
    }

    static String stringify(Document doc) {
	TransformerFactory factory = TransformerFactory.newInstance();
	try {
	    Transformer transformer = factory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    StringWriter writer = new StringWriter();
	    transformer.transform(new DOMSource(doc), new StreamResult(writer));
	    return writer.toString();
	} catch (TransformerException e) {
	    LoggerFactory.getLogger(XMLUtil.class).error(e.getMessage(), e);
	    return null;
	}
    }

}
