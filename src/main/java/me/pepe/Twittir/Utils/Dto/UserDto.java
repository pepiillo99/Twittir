package me.pepe.Twittir.Utils.Dto;

public class UserDto extends Dto {
	private String userName = "test";
	private String name = "test1";
	private boolean verified = false;
	public UserDto() {}
	public UserDto(String userName, String name, boolean verified) {
		this.userName = userName;
		this.name = name;
		this.verified = verified;
	}
	public String getName() {
		return name;
	}
	public String getUserName() {
		return userName;
	}
	public boolean isVerified() {
		return verified;
	}

}
