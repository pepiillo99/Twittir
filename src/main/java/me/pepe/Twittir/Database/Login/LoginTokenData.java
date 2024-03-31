package me.pepe.Twittir.Database.Login;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LoginTokenData")
public class LoginTokenData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private long userID;
	private String loginKey;
	private long created;
	private long expire;
	private String agent;
	public LoginTokenData() {}
	public LoginTokenData(long id, long userID, String loginKey, long created, long expire, String agent) {
		this.id = id;
		this.userID = userID;
		this.loginKey = loginKey;
		this.created = created;
		this.expire = expire;
		this.agent = agent;
	}
	public long getId() {
		return id;
	}
	public long getUserID() {
		return userID;
	}
	public String getLoginKey() {
		return loginKey;
	}
	public long getCreated() {
		return created;
	}
	public long getExpire() {
		return expire;
	}
	public String getAgent() {
		return agent;
	}
}