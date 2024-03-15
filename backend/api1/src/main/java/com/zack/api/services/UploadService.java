package com.zack.api.services;


import com.zack.api.components.files.FileManager;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private final List<String> VALID_PROFILE_TYPES = Arrays.asList("jpg", "png", "jpeg");
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileManager fileManager;
    private final Path pathUpload;


    UploadService() {
        pathUpload = Paths.get("").toAbsolutePath().resolve("public").resolve("files");

    }

    public Response generateFileProfileInServer(InputStream fileInput, String filename) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            UserModel user = (UserModel) authentication.getPrincipal();

            String[] fileDetails = filename.split("\\.");

            if (!VALID_PROFILE_TYPES.contains(fileDetails[1])) {
                throw new BadRequestException(GlobalResponses.ONLY_IMAGES.getText());
            }
            String realNameFile = user.getId() + ".png";
            String filePath = pathUpload + "/" + realNameFile;

            fileManager.writeStreamFile(filePath, fileInput);

            String apiUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            user.setProfile(apiUrl + "/files/" + realNameFile);
            userRepository.save(user);

            return new Response(GlobalResponses.USER_UPDATED_PROFILE.getText());
        } else {
            throw new ForbiddenException(GlobalResponses.USER_FORBIDDEN.getText());
        }


    }
}
