package me.pepe.Twittir.Utils.Dto;

public class ErrorDto extends Dto {
    private String error;
    public ErrorDto(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}