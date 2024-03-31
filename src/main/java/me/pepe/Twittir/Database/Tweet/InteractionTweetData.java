package me.pepe.Twittir.Database.Tweet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "InteractionTweetData")
public class InteractionTweetData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int tweetID = 0;
	private int userID = 0;
	private boolean retweeted = false;
	private boolean liked = false;
	public InteractionTweetData() {}
	public InteractionTweetData(int id, int tweetID, int userID, boolean shared, boolean liked) {
		this.id = id;
		this.tweetID = tweetID;
		this.userID = userID;
		this.retweeted = shared;
		this.liked = liked;
	}
	public int getID() {
		return id;
	}
	public int getTweetID() {
		return tweetID;
	}
	public int getUserID() {
		return userID;
	}
	public boolean isRetweeted() {
		return retweeted;
	}
	public boolean isLiked() {
		return liked;
	}
	@Override
	public String toString() {
		return "{" +
				"\"id\":" + id +
				", \"tweetID\":" + tweetID +
				", \"userID\":" + userID +
				", \"retweeted\":" + retweeted +
				", \"liked\":" + liked +
				'}';
	}
}
