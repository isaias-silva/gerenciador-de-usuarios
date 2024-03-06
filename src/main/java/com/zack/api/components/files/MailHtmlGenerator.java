package com.zack.api.components.files;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

@Component
public class MailHtmlGenerator {

    private final Path path;

    MailHtmlGenerator() {
        path = Paths.get("")
                .toAbsolutePath()
                .resolve("src")
                .resolve("main")
                .resolve("resources")
                .resolve("mail-templates");

    }

    public String generatorMailFile(String fileName, String name, String text, Optional<String> code) throws IOException {

        StringBuilder content= new StringBuilder();

        Path pathMailFile = path.resolve(fileName + ".html");
        File file = new File(pathMailFile.toString());
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;

        System.out.println(code.get());
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
