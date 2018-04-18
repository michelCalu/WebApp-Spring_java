package be.unamur.hermes.business.service;

import java.util.Locale;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.common.base.Charsets;

import be.unamur.hermes.business.document.DocumentCreationRequest;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final TemplateEngine templateEngine;
    private final TemplateResolver templateResolver;

    public DocumentServiceImpl() {
	this.templateEngine = new TemplateEngine();
	this.templateResolver = initResolver();
	this.templateEngine.setTemplateResolver(templateResolver);
    }

    private TemplateResolver initResolver() {
	TemplateResolver result = new FileTemplateResolver();
	result.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
	result.setPrefix("/templates/");
	result.setSuffix(".html");
	result.setCharacterEncoding(Charsets.UTF_8.name());
	result.setCacheable(false);
	return result;
    }

    @Override
    public String getNationalityCertificate(boolean positive, DocumentCreationRequest document) {
	String templateName = positive ? "nationalityCertificate/positive" : "nationalityCertificate/negative";
	IContext context = initContext(document);
	return templateEngine.process(templateName, context);
    }

    protected IContext initContext(DocumentCreationRequest document) {
	Context result = new Context(Locale.FRENCH);
	// result.setVariable(name, value);

	return result;
    }

}
