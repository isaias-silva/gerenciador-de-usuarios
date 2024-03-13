package com.zack.api.util.responses.bodies;

import java.util.*;

public class MailData {

    private UUID id;
    private String subject;
    private String to;
    private String from ;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MailData(UUID id,String subject, String to, String from){
        this.id=id;
        this.to=to;
        this.subject=subject;
        this.from=from;
    }
    public MailData(Object object){


        if(object.getClass().isArray()){
           List<?> list = Arrays.asList((Object[])object);
           this.id=(UUID) list.get(0);
           this.to=(String) list.get(1);
            this.from=(String) list.get(2);
            this.subject=(String) list.get(3);

        }



    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
