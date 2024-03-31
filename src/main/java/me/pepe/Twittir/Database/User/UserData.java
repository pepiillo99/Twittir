package me.pepe.Twittir.Database.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserData")
public class UserData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String userName = "test";
	private String name = "test1";
	private boolean verified = false;
	public UserData() {}
	public UserData(long id, String userName, String name, boolean verified) {
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.verified = verified;
	}
	public long getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUserName() {
		return userName;
	}
	public boolean isVerified() {
		return verified;
	}
}