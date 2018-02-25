package be.unamur.hermes.business.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"be.unamur.hermes.business.service"})
// Mise en place par Spring de tout l'aspect atomic, securité, ... pour les méthode @Transactional
@EnableTransactionManagement
public class BusinessConfiguration {

    private DataSource dataSource;

    @Autowired
    public BusinessConfiguration(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
