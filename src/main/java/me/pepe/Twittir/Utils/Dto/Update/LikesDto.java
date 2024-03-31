package me.pepe.Twittir.Utils.Dto.Update;

public class LikesDto extends UpdateDto {
	private int likes;
	public LikesDto() {}
	public LikesDto(boolean updated, int likes) {
		super(updated);
		this.likes = likes;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
}
