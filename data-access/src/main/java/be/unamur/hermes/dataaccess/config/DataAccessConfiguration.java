package be.unamur.hermes.dataaccess.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Spring prend cette classe en compte pour ton application context
@Configuration
// Fait un scan de mes sources dans le package {"..."}
@ComponentScan(basePackages = {"be.unamur.hermes.dataaccess.repository"})
public class DataAccessConfiguration {

    //DataSourceAutoConfiguration
    //DataSourceConfiguration
    //DataSourceBuilder & -> DatabaseDriver to determine the driver
    //DataSourcePoolMetadataProvidersConfiguration -> HikariPoolDataSourceMetadataProviderConfiguration

}
