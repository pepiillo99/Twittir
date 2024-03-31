package me.pepe.Twittir.Utils.Dto.Update;

import me.pepe.Twittir.Utils.Dto.FollowCounterDto;

public class FollowDto extends UpdateDto {
	private FollowCounterDto counter;
	public FollowDto() {}
	public FollowDto(boolean update, FollowCounterDto counter) {
		super(update);
		this.counter = counter;
	}
	public FollowCounterDto getCounter() {
		return counter;
	}
}
