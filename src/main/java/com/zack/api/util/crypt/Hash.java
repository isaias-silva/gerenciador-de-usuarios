package com.zack.api.util.crypt;

import org.springframework.security.crypto.bcrypt.BCrypt;


public class Hash {

    public String generateHash(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public boolean compareHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
