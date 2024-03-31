package me.pepe.Twittir.Auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication extends AbstractAuthenticationToken {
	private static final long serialVersionUID = -8882705191154631191L;
	private Object credentials;
	private long userID;
	public UserAuthentication(Object credentials, Object userData, long userID) {
		super(Collections.emptyList());
		this.credentials = credentials;
		this.setDetails(userData);
		this.userID = userID;
		this.setAuthenticated(true);
	}
	public UserAuthentication(Object credentials, Object userData, long userID, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.credentials = credentials;
		this.setDetails(userData);
		this.userID = userID;
		super.setAuthenticated(true); 
	}
	@Override
	public Object getCredentials() {
		return this.credentials;
	}
	@Override
	public Object getPrincipal() {
		return credentials;
	}
	public long getUserID() {
		return userID;
	}
}