package com.zack.api.util.responses.enums;

public enum GlobalResponses {
    USER_NOT_FOUND("usuário não encontrado"),
    USER_ALREADY_EXISTS("usuário já existe"),
    USER_REGISTERED("usuário cadastrado com sucesso!"),

    USER_INCORRECT_PASSWORD("senha incorreta"),
    USER_LOGGED("usuário logado com sucesso");


    private String text;

    GlobalResponses(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
