package be.unamur.hermes.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

import be.unamur.hermes.web.security.HermesPermissionEvaluator;

/**
 * The only 'raison d'etre' (as the British say) of this class is to configure
 * the custom {@link HermesPermissionEvaluator}. See this class for more
 * details.
 * 
 * @author Thomas_Elskens
 *
 */
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class MethodSecurityConfig /* extends GlobalMethodSecurityConfiguration */ {

    // @Override
    // protected MethodSecurityExpressionHandler createExpressionHandler() {
    // DefaultMethodSecurityExpressionHandler expressionHandler = new
    // DefaultMethodSecurityExpressionHandler();
    // expressionHandler.setPermissionEvaluator(new HermesPermissionEvaluator());
    // return expressionHandler;
    // }

    @Autowired
    private HermesPermissionEvaluator permissionEvaluator;

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
	DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
	handler.setPermissionEvaluator(permissionEvaluator);
	return handler;
    }

}
