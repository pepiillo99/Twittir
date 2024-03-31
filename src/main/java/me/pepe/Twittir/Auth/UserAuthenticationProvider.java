package me.pepe.Twittir.Auth;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import me.pepe.Twittir.Database.Login.LoginRepository;
import me.pepe.Twittir.Database.Login.LoginTokenData;
import me.pepe.Twittir.Database.User.UserData;
import me.pepe.Twittir.Service.Service;
import me.pepe.Twittir.Utils.Dto.Credentials.CredentialsDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider {
    private String secretKey = "KD903DJF398WJF27834TH348FN54G9HJ98RDHRO9P54YHKS0HG79EGJZ90VT589EZGN98ENM9GM5984";
	@Autowired
    private LoginRepository loginRepository;
	@Autowired
    private Service service;
    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    public String createToken(String login) {
    	System.out.println("login: " + login);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer(login).withIssuedAt(now).withExpiresAt(validity).sign(algorithm);
    }
    public UserAuthentication validateToken(String token, String agent) {
    	System.out.println("token: " + token);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decoded = verifier.verify(token);
            System.out.println("Login token: " + decoded.getIssuer());
            Optional<LoginTokenData> ltd = loginRepository.findByLoginKey(decoded.getIssuer());
            if (ltd.isPresent()) {
            	if (ltd.get().getAgent().equals(agent)) {
            		System.out.println("user authentication generado!");
                    return new UserAuthentication(ltd.get(), null, ltd.get().getUserID());
            	} else {
                	System.out.println("user-agent no es igual al token!, se ha eliminado el token y notificado al usuario...");
            		loginRepository.delete(ltd.get().getUserID());
            		// notificar al usuario sobre un posible fallo de seguridad en su cuenta
            		return null;
            	}
            } else {
        		System.out.println("No tiene logintoken en la db");
            	return null;
            }
        } catch (JWTVerificationException e) {
        	System.out.println("fallo de verificación del token");
        	return null;
        }
    }
    public UserAuthentication validateCredentials(CredentialsDto credentialsDto) {
    	UserData userData = service.authenticate(credentialsDto);
    	if (userData != null) {
    		System.out.println("credenciales validadas!");
    		return new UserAuthentication(credentialsDto, userData, userData.getID());
    	} else {
    		return null;
    	}
    }
}