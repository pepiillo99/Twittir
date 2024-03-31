package me.pepe.Twittir.Utils.Dto.Credentials;

import me.pepe.Twittir.Utils.Dto.Dto;

public class CredentialsDto extends Dto {
    private String username;
    private char[] password;
    public CredentialsDto() {}
    public CredentialsDto(String username, char[] password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public char[] getPassword() {
        return password;
    }
}