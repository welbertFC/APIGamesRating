package com.br.API.GamesRating.model.enums;

public enum LikeditEnum {

    LIKE(1, "Like"),
    DISLIKE(2, "Dislike"),
    NULL(3, "Null");

    private Integer code;
    private String description;

    LikeditEnum(Integer code, String description) {
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

    public static LikeditEnum toEnum(Integer code){
        if(code == null){
            return null;
        }

        for (LikeditEnum x : LikeditEnum.values()){
            if(code.equals(x.code)){
                return x;
            }
        }
        throw new IllegalArgumentException("Id Invalido" + code);
    }
}
