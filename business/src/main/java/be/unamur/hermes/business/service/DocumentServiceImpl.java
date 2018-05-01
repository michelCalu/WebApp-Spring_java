package be.unamur.hermes.business.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.common.base.Charsets;

import be.unamur.hermes.business.document.DocumentCreationRequest;
import be.unamur.hermes.business.document.DocumentHelper;
import be.unamur.hermes.common.util.PDFCreator;
import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Department;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Municipality;
import be.unamur.hermes.dataaccess.entity.Request;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final TemplateEngine templateEngine;
    private final TemplateResolver templateResolver;

    public DocumentServiceImpl() {
	this.templateEngine = new TemplateEngine();
	this.templateResolver = initResolver();
	this.templateEngine.setTemplateResolver(templateResolver);
	this.templateEngine.addDialect(new Java8TimeDialect());
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
    public String getNationalityCertificate(boolean positive, DocumentCreationRequest document) {
	String templateName = positive ? "nationalityCertificate/positive" : "nationalityCertificate/negative";
	Context context = initContext(document);
	context.setVariable("title", "Demande de certificat de nationalité");
	return templateEngine.process(templateName, context);
    }

    @Override
    public void getNationalityCertificate(boolean positive, DocumentCreationRequest document, Writer writer) {
	String templateName = positive ? "nationalityCertificate/positive" : "nationalityCertificate/negative";
	Context context = initContext(document);
	context.setVariable("title", "Demande de certificat de nationalité");
	templateEngine.process(templateName, context, writer);
    }

    public String getParkingCardDecision(boolean positive, DocumentCreationRequest document) {
	String templateName = positive ? "parkingCard/positive" : "parkingCard/negative";
	Context context = initContext(document);
	context.setVariable("title", "Demande de carte de stationnement pour riverain ou visiteur");
	return templateEngine.process(templateName, context);
    }

    public String getParkingCard(DocumentCreationRequest document) {
	Context context = initContext(document);
	return templateEngine.process("parkingCard/card", context);
    }

    public String getPayment(DocumentCreationRequest document) {
	Context context = initContext(document);
	context.setVariable("title", "Invitation à payer");
	return templateEngine.process("parkingCard/payment", context);
    }

    protected Context initContext(DocumentCreationRequest document) {
	Context result = new Context(Locale.FRENCH);
	result.setVariable("date", LocalDate.now());
	result.setVariable("requestor", document.getRequestor());
	result.setVariable("request", document.getRequest());
	result.setVariable("officer", document.getOfficer());
	result.setVariable("department", document.getDepartment());
	result.setVariable("municipality", document.getDepartment().getMunicipality());
	result.setVariable("util", new DocumentHelper());
	return result;
    }

    // debugging
    public static void main(String[] args) {
	DocumentCreationRequest doc = new DocumentCreationRequest();
	Citizen mayor = new Citizen(1L, "mayorFirstName", "mayorLastName", null, "mayorMail@commune.be", "mayorPhone",
		"mayorNRN", null);
	Citizen requestor = new Citizen(2L, "requestorFirstName", "requestorLastName",
		new Address(3, "Rue Haute", 130, "B", 1230, "Gembloux", "BRC", "Belgium"), "requestorMail@hotmail.com",
		"requestorPhone", "requestorNRN", LocalDate.of(1970, 5, 1));
	Municipality munip = new Municipality();
	munip.setName("Gembloux");
	munip.setMayor(mayor);
	munip.setAddress(new Address(2L, "Rue Champs d'Eglise", 13, null, 1230, "Gembloux", "Wallonie", "Belgium"));
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
	doc.setDepartment(dep);
	doc.setRequestor(requestor);
	doc.setOfficer(officer);
	Request request = new Request(1L, 1L);
	request.setAssignee(officer);
	request.setCitizen(requestor);
	request.setStatus("Done");
	request.setSystemRef("HERM-REF");
	request.setUserRef("USER_REF");

	doc.setRequest(request);
	DocumentServiceImpl service = new DocumentServiceImpl();
	String result = service.getNationalityCertificate(false, doc);
	// service.getParkingCardDecision(true, doc);
	// service.getPayment(doc);
	// service.getParkingCard(doc);
	System.out.println(result);
	PDFCreator creator = new PDFCreator();
	try {
	    Path output = Paths.get("C:\\Users\\Thomas_Elskens\\Documents\\test", "test.pdf");
	    creator.createPDF(result, output);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
