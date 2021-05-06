package com.br.API.GamesRating.model.enums;

public enum NoteEnum {

    ONESTAR(1, "Uma Estrela"),
    TWOSTARS(2, "Duas Estrelas"),
    THREESTAR(3, "Três Estrela"),
    FOURSTARS(4, "Quatro Estrelas"),
    FIVESTARS(5, "Cinco Estrelas");

    private Integer code;
    private String description;

    NoteEnum(Integer code, String description) {
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

    public static NoteEnum toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (NoteEnum x : NoteEnum.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id invalido " + code);
    }
}
