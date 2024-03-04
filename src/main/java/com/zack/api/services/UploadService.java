package com.zack.api.services;


import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import com.zack.api.util.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {

    @Autowired
    UserRepository userRepository;
    public void generateFileProfileInServer(InputStream file, String filename) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            UserModel user = (UserModel) authentication.getPrincipal();

            Path pathUpload= Paths.get("").toAbsolutePath().resolve("public").resolve("files");
            String[] fileDetails=filename.split("\\.");

            String realNameFile=user.getId() +"."+ fileDetails[1];

            String filePath=pathUpload.toString()+"/"+realNameFile;

            try {
                OutputStream outputStream = new FileOutputStream(filePath);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = file.read(buffer)) != -1) {
                    System.out.println("file upload: " + length / 1024 + "kb");
                    outputStream.write(buffer, 0, length);
                }
            }catch (FileNotFoundException | SecurityException e){
                throw new RuntimeException("erro ao fazer upload de arquivo");
            }
        }else{
            throw new ForbiddenException("não autorizado.");
        }


    }
}
