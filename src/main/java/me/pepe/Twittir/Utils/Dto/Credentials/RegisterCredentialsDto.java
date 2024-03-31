package me.pepe.Twittir.Utils.Dto.Credentials;

import me.pepe.Twittir.Utils.Dto.Dto;

public class RegisterCredentialsDto extends Dto {
    private String name;
    private String username;
    private char[] password;
    public RegisterCredentialsDto() {}
    public RegisterCredentialsDto(String name, String username, char[] password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public char[] getPassword() {
        return password;
    }
}