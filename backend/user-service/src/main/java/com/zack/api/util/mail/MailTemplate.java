package com.zack.api.util.mail;

public enum MailTemplate {
    CODE_VERIFICATION("bem vindo", "seja bem vindo a MediaCodec! para válidar o email da sua conta use o código: "),
    EMAIL_VALIDATED("email validado!", "olá seu endereço de email foi validado com sucesso! aproveite a plataforma!"),
    NEW_CODE_VERIFICATION("código de verificação", "um novo código de verificação foi solicitado, use o código abaixo para validar seu e-mail:"),
    PASSWORD_FORGOTTEN("recuperação de senha", "foi solicitada a troca de senha desta conta, use esse código para alterar sua senha (se não foi você desconsidere)"),
    EMAIL_CHANGED("troca de e-mail", "foi solicitada a troca de e-mail da sua conta para esse e-mail, use o código abaixo para confirmar a troca (se não foi você desconsidere)");

    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    MailTemplate(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

}
