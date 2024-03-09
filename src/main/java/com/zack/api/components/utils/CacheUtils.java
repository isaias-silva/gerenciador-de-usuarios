package com.zack.api.components.utils;

import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.responses.enums.GlobalResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CacheUtils {

    @Autowired

    private CacheManager cacheManager;

    @Cacheable(value = "code", key = "#userMail")
    public String cacheRandomCode(String userMail) {
        int length = 7;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        String code = sb.toString();
        return code;
    }

    @Cacheable(value = "code", key = "#userMail")
    public String getCodeFromCache(String userMail) {

        return cacheRandomCode(userMail);
    }

    @CacheEvict(value = "code", key = "#userMail")
    public void clearCacheRandom(String userMail) {
    }

    @Cacheable(value = "newMail", key = "#id")

    public String cacheNewMail(String newMail, String id) {
        System.out.println(newMail);
        return newMail;
    }

    public String getCachedNewMail(String id) {
        Cache cache = cacheManager.getCache("newMail");
        if (cache != null) {
            String newMail = cache.get(id, String.class);
            if (newMail == null) {
                throw new NotFoundException(GlobalResponses.NOT_FOUND_MAIL_CACHED.getText());
            }
            return newMail;
        } else {
            throw new RuntimeException(GlobalResponses.NOT_FOUND_MAIL_CACHED.getText());
        }

    }
    @CacheEvict(value = "newMail", key = "#id")
    public void clearCacheNewMail(String id) {
    }

}
