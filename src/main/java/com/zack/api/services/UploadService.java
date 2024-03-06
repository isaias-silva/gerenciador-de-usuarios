package com.zack.api.services;


import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import com.zack.api.util.exceptions.ForbiddenException;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.enums.GlobalResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private final List<String> VALID_PROFILE_TYPES= Arrays.asList("jpg","png","jpeg");
    @Autowired
    UserRepository userRepository;
    public Response generateFileProfileInServer(InputStream file, String filename) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            UserModel user = (UserModel) authentication.getPrincipal();

            String[] fileDetails=filename.split("\\.");

            if(!VALID_PROFILE_TYPES.contains(fileDetails[1])){
                throw new BadRequestException(GlobalResponses.ONLY_IMAGES.getText());
            }

            Path pathUpload= Paths.get("").toAbsolutePath().resolve("public").resolve("files");
            String realNameFile=user.getId() +".png";
            String filePath=pathUpload+"/"+realNameFile;

                OutputStream outputStream = new FileOutputStream(filePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = file.read(buffer)) != -1) {
                    System.out.println("file upload: " + length / 1024 + "kb");
                    outputStream.write(buffer, 0, length);
                }

            String apiUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            user.setProfile(apiUrl+"/files/"+realNameFile);
            userRepository.save(user);

            return new Response(GlobalResponses.USER_UPDATED_PROFILE.getText());
        }else{
            throw new ForbiddenException(GlobalResponses.USER_FORBIDDEN.getText());
        }


    }
}
