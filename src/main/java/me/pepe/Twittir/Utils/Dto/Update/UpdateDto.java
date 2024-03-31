package me.pepe.Twittir.Utils.Dto.Update;

import me.pepe.Twittir.Utils.Dto.Dto;

public abstract class UpdateDto extends Dto {
	private boolean update;
	public UpdateDto() {}
	public UpdateDto(boolean update) {
		this.update = update;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
}
