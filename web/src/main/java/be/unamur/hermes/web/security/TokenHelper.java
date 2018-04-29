package be.unamur.hermes.web.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenHelper {

    private String APP_NAME = "Hermes";
    public String SECRET = "queenvictoria";
    private String AUTH_HEADER = "Authorization";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
	String username;
	try {
	    final Claims claims = this.getAllClaimsFromToken(token);
	    username = claims.getSubject();
	} catch (Exception e) {
	    username = null;
	}
	return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
	Date issueAt;
	try {
	    final Claims claims = this.getAllClaimsFromToken(token);
	    issueAt = claims.getIssuedAt();
	} catch (Exception e) {
	    issueAt = null;
	}
	return issueAt;
    }

    public String getAudienceFromToken(String token) {
	String audience;
	try {
	    final Claims claims = this.getAllClaimsFromToken(token);
	    audience = claims.getAudience();
	} catch (Exception e) {
	    audience = null;
	}
	return audience;
    }

    public String refreshToken(String token) {
	String refreshedToken;
	try {
	    final Claims claims = this.getAllClaimsFromToken(token);
	    claims.setIssuedAt(new Date());
	    refreshedToken = Jwts.builder().setClaims(claims).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	} catch (Exception e) {
	    refreshedToken = null;
	}
	return refreshedToken;
    }

    public String generateToken(String username) {
	return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setAudience("web").setIssuedAt(new Date())
		.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private Claims getAllClaimsFromToken(String token) {
	Claims claims;
	try {
	    claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	} catch (Exception e) {
	    claims = null;
	}
	return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
	final String username = getUsernameFromToken(token);
	return (username != null && username.equals(userDetails.getUsername()));
    }

    public String getToken(HttpServletRequest request) {
	/**
	 * Getting the token from Authentication header e.g Bearer your_token
	 */
	String authHeader = getAuthHeaderFromHeader(request);
	if (authHeader != null && authHeader.startsWith("Bearer ")) {
	    return authHeader.substring(7);
	}
	return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
	return request.getHeader(AUTH_HEADER);
    }
}
