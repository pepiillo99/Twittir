package me.pepe.Twittir.Database.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserFollowData")
public class UserFollowData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int userID;
	private int followID; // usuario al que sigue
	public UserFollowData() {}
	public UserFollowData(int id, int userID, int followID) {
		this.id = id;
		this.userID = userID;
		this.followID = followID;
	}
	public int getID() {
		return id;
	}
	public int getUserID() {
		return userID;
	}
	public int getFollowID() {
		return followID;
	}
}
