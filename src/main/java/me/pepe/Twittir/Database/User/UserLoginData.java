package me.pepe.Twittir.Database.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserLoginData")
public class UserLoginData {
	@Id
	private long userID;
	private String password = "test1";
	public UserLoginData() {}
	public UserLoginData(long userID, String password) {
		this.userID = userID;
		this.password = password;
	}
	public long getUserID() {
		return userID;
	}
	public String getPassword() {
		return password;
	}
}