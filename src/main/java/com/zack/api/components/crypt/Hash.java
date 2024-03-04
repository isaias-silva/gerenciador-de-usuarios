package com.zack.api.components.crypt;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;



@Component
public class Hash {

    public String generateHash(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }


    public boolean compareHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
