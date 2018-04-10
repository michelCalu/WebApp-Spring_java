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

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }

}
