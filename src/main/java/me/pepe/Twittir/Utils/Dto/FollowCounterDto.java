package me.pepe.Twittir.Utils.Dto;

public class FollowCounterDto {
	private boolean follow;
	private int following;
	private int followers;
	public FollowCounterDto() {}
	public FollowCounterDto(boolean follow, int following, int followers) {
		this.follow = follow;
		this.following = following;
		this.followers = followers;
	}
	public boolean isFollow() {
		return follow;
	}
	public int getFollowing() {
		return following;
	}
	public int getFollowers() {
		return followers;
	}	
}
