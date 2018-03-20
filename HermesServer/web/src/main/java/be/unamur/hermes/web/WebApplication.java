package be.unamur.hermes.web;

import be.unamur.hermes.business.config.BusinessConfiguration;
import be.unamur.hermes.dataaccess.config.DataAccessConfiguration;
import be.unamur.hermes.web.config.WebConfiguration;
import be.unamur.hermes.web.config.WebSecurityConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfiguration.class, BusinessConfiguration.class, DataAccessConfiguration.class, WebSecurityConfiguration.class})
public class WebApplication extends SpringBootServletInitializer {

    // Spring va créé un dispatcher servlet.
    // Il va checker toutes les classes avec @controller
    // Et c'est dans les controller qu'on défini les mapping  (../author, ../todo,...)
    // Spring va demander : "Toi tu peux gérer cet appel ? non ok toi ? non, ... (au plus précis)

    // Le retour d'un controller est soit
    //   - json,
    //   - une view (de type timeleaf) via le ViewResolver

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }

}
