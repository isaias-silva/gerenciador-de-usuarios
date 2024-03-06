package com.zack.api.components.utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class GenerateRandom {
   @Cacheable(value = "code",key = "#userMail")
    public String randomCode(String userMail){
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

       return randomCode(userMail);
    }

    @CacheEvict(value = "code", key = "#userMail")
    public void clearCache(String userMail) {}
}
