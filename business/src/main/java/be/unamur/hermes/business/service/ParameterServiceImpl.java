package be.unamur.hermes.business.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.util.XMLUtil;
import be.unamur.hermes.dataaccess.entity.RequestParameters;
import be.unamur.hermes.dataaccess.entity.RequestType;
import be.unamur.hermes.dataaccess.repository.ParameterRepository;

@Service
public class ParameterServiceImpl implements ParameterService {

    private static String TAG_PARAMETERS = "parameters";
    private static String TAG_PARAMETER = "parameter";
    private static String ATTR_ID = "id";

    private static Logger logger = LoggerFactory.getLogger(ParameterServiceImpl.class);

    private final ParameterRepository parameterRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    public ParameterServiceImpl(ParameterRepository parameterRepository) {
	super();
	this.parameterRepository = parameterRepository;
    }

    @Override
    public String getParameter(long municipality, long requestTypeId, String parameterId) {
	RequestParameters claimType = getParameters(municipality, requestTypeId);
	return claimType == null ? null : String.valueOf(claimType.getParameter(parameterId));
    }

    @Override
    public RequestParameters getParameters(long municipalityId, long requestTypeId) {
	String contents = parameterRepository.findContents(municipalityId, requestTypeId);
	try {
	    Map<String, String> parsed = parse(contents);
	    return new RequestParameters(municipalityId, requestTypeId, parsed);
	} catch (Exception e) {
	    throw new BusinessException(e.getMessage());
	}
    }

    @Override
    public RequestParameters getParameters(long municipalityId, String requestTypeDescription) {
	RequestType requestType = requestService.findRequestTypeByDescription(requestTypeDescription);
	if (requestType == null)
	    throw new BusinessException("Unknown requestType : " + requestTypeDescription);
	return getParameters(municipalityId, requestType.getId());
    }

    @Override
    public void create(Map<String, String> parameters, long municipalityId, long requestTypeId) {
	String xmlString = toXMLString(parameters);
	parameterRepository.create(municipalityId, requestTypeId, xmlString);
    }

    @Override
    public void update(Map<String, String> newParameters, long municipalityId, long requestTypeId) {
	// TODO validation on contents
	RequestParameters params = getParameters(municipalityId, requestTypeId);
	params.getParameters().putAll(newParameters);
	String newContents = toXMLString(params.getParameters());
	parameterRepository.updateContents(municipalityId, requestTypeId, newContents);
    }

    private Map<String, String> parse(String contents) throws Exception {
	Document doc = XMLUtil.toDocument(contents);
	List<Element> elems = DomUtils.getChildElementsByTagName(doc.getDocumentElement(), TAG_PARAMETER);
	return elems.stream().collect(Collectors.toMap(elem -> elem.getAttribute(ATTR_ID), Element::getTextContent));
    }

    private String toXMLString(Map<String, String> params) {
	DocumentBuilder builder = XMLUtil.getBuilder();
	Document doc = builder.newDocument();
	doc.setXmlStandalone(true);
	Element root = doc.createElement(TAG_PARAMETERS);
	doc.appendChild(root);
	for (Entry<String, String> entry : params.entrySet()) {
	    Element paramElement = doc.createElement(TAG_PARAMETER);
	    paramElement.setAttribute(ATTR_ID, entry.getKey());
	    paramElement.setTextContent(entry.getValue());
	    root.appendChild(paramElement);
	}
	return XMLUtil.stringify(doc);
    }
}
