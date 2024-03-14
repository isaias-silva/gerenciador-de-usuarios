package com.zack.api.components.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

@Component
public class MailHtmlGenerator {

    private final Path path;
    private static final Logger logger = LoggerFactory.getLogger(MailHtmlGenerator.class);

    MailHtmlGenerator() {
        path = Paths.get("src","main","resources","mail-templates");
        logger.info(" file path of mail templates: "+path.toAbsolutePath().toString());

        if(!Files.exists(path)){
            throw new RuntimeException("file path not found");
        }


    }

    public String generatorMailFile(String name, String text, Optional<String> code) throws IOException {

        StringBuilder content= new StringBuilder();

        Path pathMailFile = path.resolve((code.isPresent()?"default.verify":"default.message")+ ".html");
        File file = new File(pathMailFile.toString());
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;


        while ((line = bufferedReader.readLine()) != null) {
          String replacedLine=line;
           if(line.contains("[NAME]")) replacedLine=line.replace("[NAME]",name) ;
           if(line.contains("[TEXT]")) replacedLine=line.replace("[TEXT]",text) ;
           if(line.contains("[CODE]") && code.isPresent()) replacedLine=line.replace("[CODE]",code.get()) ;
           
           content.append(replacedLine);
          
        }

        bufferedReader.close();
        fileReader.close();

        return content.toString();

    }
}
