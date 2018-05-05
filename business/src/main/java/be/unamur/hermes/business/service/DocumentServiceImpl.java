package be.unamur.hermes.business.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.common.base.Charsets;

import be.unamur.hermes.business.document.DocumentHelper;
import be.unamur.hermes.common.util.PDFCreator;
import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestStatus;
import be.unamur.hermes.dataaccess.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final TemplateEngine templateEngine;
    private final TemplateResolver templateResolver;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
	this.documentRepository = documentRepository;
	this.templateEngine = new TemplateEngine();
	this.templateResolver = initResolver();
	this.templateEngine.setTemplateResolver(templateResolver);
	this.templateEngine.addDialect(new Java8TimeDialect());
    }

    private DocumentServiceImpl() {
	this(null);
    }

    private TemplateResolver initResolver() {
	TemplateResolver result = new ClassLoaderTemplateResolver();
	result.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
	result.setPrefix("templates/");
	result.setSuffix(".html");
	result.setCharacterEncoding(Charsets.UTF_8.name());
	result.setCacheable(false);
	return result;
    }

    @Override
    public InputStream findDocumentById(long documentId) {
	String htmlContents = documentRepository.getDocument(documentId);
	try {
	    return PDFCreator.createPDF(htmlContents);
	} catch (IOException e) {
	    logger.error("PDF creation failed", e);
	}
	return null;
    }

    @Override
    public List<Long> findDocumentByRequest(long requestId) {
	return documentRepository.getDocumentIds(requestId);
    }

    @Override
    public long createNationalityCertificate(boolean positive, Request request) {
	String templateName = positive ? "nationalityCertificate/positive" : "nationalityCertificate/negative";
	Context context = initContext(request);
	context.setVariable("title", "Demande de certificat de nationalité");
	String contents = templateEngine.process(templateName, context);
	return documentRepository.create(request.getId(), contents);
    }

    @Override
    public long createParkingCardDecision(boolean positive, Request request) {
	String templateName = positive ? "parkingCard/positive" : "parkingCard/negative";
	Context context = initContext(request);
	context.setVariable("title", "Demande de carte de stationnement pour riverain ou visiteur");
	String contents = templateEngine.process(templateName, context);
	return documentRepository.create(request.getId(), contents);
    }

    @Override
    public long createParkingCard(Request request) {
	Context context = initContext(request);
	String contents = templateEngine.process("parkingCard/card", context);
	return documentRepository.create(request.getId(), contents);
    }

    @Override
    public long createPayment(Request request) {
	Context context = initContext(request);
	context.setVariable("title", "Invitation à payer");
	String contents = templateEngine.process("parkingCard/payment", context);
	return documentRepository.create(request.getId(), contents);
    }

    protected Context initContext(Request request) {
	Context result = new Context(Locale.FRENCH);
	result.setVariable("date", LocalDate.now());
	result.setVariable("requestor", request.getCitizen());
	result.setVariable("request", request);
	result.setVariable("officer", request.getAssignee());
	if (request.getDepartment().getAddress() == null) {
	    request.getDepartment().setAddress(request.getDepartment().getMunicipality().getAddress());
	}
	result.setVariable("department", request.getDepartment());
	result.setVariable("municipality", request.getDepartment().getMunicipality());
	result.setVariable("util", new DocumentHelper());
	return result;
    }

    // FIXME debugging
    public static void main(String[] args) {
	String mayorName = "mayorFirstName mayorLastName";
	Municipality munip = new Municipality();
	munip.setName("Gembloux");
	munip.setMayorName(mayorName);
	munip.setAddress(new Address(2L, "Rue Champs d'Eglise", 13, null, 1230, "Gembloux", "Wallonie", "Belgium"));
	Citizen requestor = new Citizen(2L, "requestorFirstName", "requestorLastName",
		new Address(3, "Rue Haute", 130, "B", 1230, "Gembloux", "BRC", "Belgium"), "requestorMail@hotmail.com",
		"requestorPhone", "requestorNRN", LocalDate.of(1970, 5, 1));
	Department dep = new Department();
	Employee officer = new Employee(1L, "officerFirstName", "officerLastName", null, "officerMail@commune.be",
		"officerPhone", "officerNRN", LocalDate.of(1983, 11, 13), "officerAccountNumber", null, 'M', null, 0,
		0);
	Employee manager = new Employee(1L, "managerFirstName", "managerLastName", null, "managerMail@commune.be",
		"managerPhone", "managerNRN", LocalDate.of(1983, 11, 13), "managerAccountNumber", null, 'M', null, 0,
		0);
	dep.setManager(manager);
	dep.setMunicipality(munip);
	dep.setAddress(new Address(1L, "Rue Champs d'Eglise", 12, null, 1230, "Gembloux", "Wallonie", "Belgium"));
	dep.setName("Service de population de Gembloux");
	dep.setEmail("population-gembloux@commune.be");
	dep.setPhoneNumber("populationPhoneNumber");
	RequestStatus requestStatus = new RequestStatus(0, "Done");
	Request request = new Request(1L, 1L);
	request.setAssignee(officer);
	request.setCitizen(requestor);
	request.setStatus(requestStatus);
	request.setSystemRef("HERM-REF");
	request.setUserRef("USER_REF");
	DocumentServiceImpl service = new DocumentServiceImpl();
	// String result = service.getNationalityCertificate(false, request);
	// service.getParkingCardDecision(true, doc);
	// service.getPayment(doc);
	// service.getParkingCard(doc);
	// System.out.println(result);
	// PDFCreator creator = new PDFCreator();
	// try {
	// Path output = Paths.get("C:\\Users\\Thomas_Elskens\\Documents\\test",
	// "test.pdf");
	// creator.createPDF(result, output);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
    }
}
