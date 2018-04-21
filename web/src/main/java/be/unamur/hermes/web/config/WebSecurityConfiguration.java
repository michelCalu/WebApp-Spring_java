package be.unamur.hermes.web.config;

import java.util.Arrays;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = { "be.unamur.hermes.web.security" })
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
	http.cors().and().csrf().disable().authorizeRequests().anyRequest().permitAll();
	/*
	 * http .csrf() .disable() .authorizeRequests()
	 * .antMatchers("/secured/**").authenticated() .anyRequest().permitAll() .and()
	 * .formLogin() .and() .logout() .logoutUrl("/logout")
	 * .invalidateHttpSession(true) .clearAuthentication(true)
	 * .logoutSuccessUrl("/") .and() .exceptionHandling()
	 * .accessDeniedPage("/error");
	 */
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
	web.debug(true);
    }

    CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();
	configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", configuration);
	return source;
    }
}
