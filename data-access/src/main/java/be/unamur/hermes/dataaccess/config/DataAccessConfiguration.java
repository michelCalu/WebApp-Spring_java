package be.unamur.hermes.dataaccess.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"be.unamur.hermes.dataaccess.repository"})
public class DataAccessConfiguration {

}
