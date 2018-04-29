package be.unamur.hermes.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.UserAccountService;
import be.unamur.hermes.dataaccess.entity.UserAccount;
import be.unamur.hermes.web.security.AuthenticationRequest;
import be.unamur.hermes.web.security.TokenHelper;
import be.unamur.hermes.web.security.UserToken;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping()
    public ResponseEntity<UserToken> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
	    HttpServletResponse response) throws AuthenticationException, IOException {

	UserAccount user = (UserAccount) userAccountService.loadUserByUsername(authenticationRequest.getUsername());

	// Perform the security
	final Authentication authentication = new UsernamePasswordAuthenticationToken(
		authenticationRequest.getUsername(), authenticationRequest.getPassword(), user.getAuthorities());
	authenticationManager.authenticate(authentication);

	// Inject into security context
	SecurityContextHolder.getContext().setAuthentication(authentication);

	// token creation
	String jws = tokenHelper.generateToken(user.getUsername());
	// Return the token
	return ResponseEntity.ok(new UserToken(jws, user.getTechnicalId()));
    }
}
