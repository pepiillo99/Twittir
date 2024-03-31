package me.pepe.Twittir.Database.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserAboutData")
public class UserAboutData {
	@Id
	private long userID;
	private String description;
	private String location;
	private long createdTime;
	private long birthdate;
	private String work;
	private String email;
	private int numberPrefix;
	private int phoneNumber;
	public UserAboutData() {}
	public UserAboutData(long userID, String description, String location, long birthdate, long createdTime, String work, String email, int numberPrefix, int phoneNumber) {
		this.userID = userID;
		this.description = description;
		this.location = location;
		this.birthdate = birthdate;
		this.createdTime = createdTime;
		this.work = work;
		this.email = email;
		this.numberPrefix = numberPrefix;
		this.phoneNumber = phoneNumber;
	}
	public long getUserID() {
		return userID;
	}
	public String getDescription() {
		return description;
	}
	public String getLocation() {
		return location;
	}
	public long getBirthDate() {
		return birthdate;
	}
	public long getCreatedTime() {
		return createdTime;
	}
	public String getWork() {
		return work;
	}
	public String getEmail() {
		return email;
	}
	public int getNumberPrefix() {
		return numberPrefix;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
}