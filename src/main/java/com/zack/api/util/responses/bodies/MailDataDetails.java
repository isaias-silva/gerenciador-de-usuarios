package com.zack.api.util.responses.bodies;

import java.util.UUID;

public class MailDataDetails extends MailData {
    private String content;

    public MailDataDetails(UUID id, String subject, String to, String from, String content) {
        super(id,subject, to, from);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
