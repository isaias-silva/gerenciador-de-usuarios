package com.zack.api.dtos;

public record EmailSendDto( String to,

                            String subject,
                            String content) {
}
