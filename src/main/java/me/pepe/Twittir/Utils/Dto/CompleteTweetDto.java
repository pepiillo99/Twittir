package me.pepe.Twittir.Utils.Dto;

import me.pepe.Twittir.Database.Tweet.TweetData;
import me.pepe.Twittir.Database.User.UserData;

public class CompleteTweetDto extends Dto {
	private int id;
	private long userID;
	private String userName = "test";
	private String name = "test1";
	private boolean userVerified = false;
	private String content = "";
	private long postTime = 0;
	private long serverTime = 0;
	private boolean shared = false;
	private boolean liked = false;
	private int retweets = 0;
	private int likes = 0;
	public CompleteTweetDto() {}
	public CompleteTweetDto(TweetData tweetData, UserData userData, boolean shared, boolean liked, int retweets, int likes) {
		this.id = tweetData.getID();
		this.userID = tweetData.getUserID();
		this.userName = userData.getUserName();
		this.name = userData.getName();
		this.userVerified = userData.isVerified();
		this.content = tweetData.getContent();
		this.postTime = tweetData.getPostTime();
		this.serverTime = System.currentTimeMillis();
		this.shared = shared;
		this.liked = liked;
		this.retweets = retweets;
		this.likes = likes;
	}
	public int getID() {
		return id;
	}
	public long getUserID() {
		return userID;
	}
	public String getUserName() {
		return userName;
	}
	public String getName() {
		return name;
	}
	public boolean isUserVerified() {
		return userVerified;
	}
	public String getContent() {
		return content;
	}
	public long getPostTime() {
		return postTime;
	}
	public long getServerTime() {
		return serverTime;
	}
	public boolean isShared() {
		return shared;
	}
	public boolean isLiked() {
		return liked;
	}
	public int getRetweets() {
		return retweets;
	}
	public int getLikes() {
		return likes;
	}
}