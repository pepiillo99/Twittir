package me.pepe.Twittir.Utils.Dto.Update;

public class RetweetsDto extends UpdateDto {
	private int retweets;
	public RetweetsDto() {}
	public RetweetsDto(boolean updated, int retweets) {
		super(updated);
		this.retweets = retweets;
	}
	public int getRetweets() {
		return retweets;
	}
	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}
}
