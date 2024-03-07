package com.zack.api.util.responses.enums;

public enum GlobalResponses {
    USER_NOT_FOUND("usuário não encontrado!"),
    USER_ALREADY_EXISTS("usuário já existe!"),
    USER_REGISTERED("usuário cadastrado com sucesso!"),
    USER_INCORRECT_PASSWORD("senha incorreta."),
    USER_LOGGED("usuário logado com sucesso!"),
    USER_UPDATED("dados atualizados!"),
    USER_FORBIDDEN("não autorizado."),
    USER_UPDATED_PROFILE("perfil atualizado!"),
    ONLY_IMAGES("apenas imagens são permitidas."),

    INVALID_TOKEN("token inválido!"),
    INVALID_CODE("código inválido."),
    NEW_CODE_SEND("um novo código foi enviado para seu e-mail."),
    MAIL_ALREADY_VALIDATED("e-mail já validado"),
    MAIL_VALIDATED("e-mail validado com sucesso.");
    private final String text;
    GlobalResponses(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

}
