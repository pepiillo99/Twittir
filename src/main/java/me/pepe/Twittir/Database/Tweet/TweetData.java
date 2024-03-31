package me.pepe.Twittir.Database.Tweet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TweetData")
public class TweetData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private long userID = 0;
	private String content = "";
	private long postTime = 0;
	public TweetData() {}
	public TweetData(int id, long userID, String content, long postTime) {
		this.id = id;
		this.userID = userID;
		this.content = content;
		this.postTime = postTime;
	}
	public int getID() {
		return id;
	}
	public long getUserID() {
		return userID;
	}
	public String getContent() {
		return content;
	}
	public long getPostTime() {
		return postTime;
	}
}