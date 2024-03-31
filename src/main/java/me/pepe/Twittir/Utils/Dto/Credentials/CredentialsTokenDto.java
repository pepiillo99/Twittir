package me.pepe.Twittir.Utils.Dto.Credentials;

import me.pepe.Twittir.Utils.Dto.Dto;

public class CredentialsTokenDto extends Dto {
    private String loginToken;
    public CredentialsTokenDto(String loginToken) {
        this.loginToken = loginToken;
    }
    public String getLoginToken() {
        return loginToken;
    }
}