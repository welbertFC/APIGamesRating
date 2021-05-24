package com.br.API.GamesRating.model.enums;

public enum UserProfile {
    USER(1, "ROLE_USER"),
    ADMIN(2, "ROLE_ADMIN");

    private Integer code;
    private String description;

    UserProfile(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static UserProfile toEnum(Integer code){
        if(code == null){
            return null;
        }

        for (UserProfile x : UserProfile.values()){
            if(code.equals(x.code)){
                return x;
            }
        }
        throw new IllegalArgumentException("Id Invalido" + code);
    }
}
