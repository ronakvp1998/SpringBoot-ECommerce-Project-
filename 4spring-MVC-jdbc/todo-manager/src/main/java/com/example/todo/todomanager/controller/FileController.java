package com.example.todo.todomanager.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);
    @PostMapping("/single")
    public String uploadSingle(@RequestParam("image")MultipartFile file) throws IOException {
        logger.info("Name : {} ", file.getName());
        logger.info("Content Type {}", file.getContentType());
        logger.info("Original File {} ",file.getOriginalFilename());
        logger.info("File Size {}",file.getSize());

        InputStream inputStream =  file.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("data.png");

        byte data[] = new byte[inputStream.available()];
        fileOutputStream.write(data) ;

        return "File Test";
    }

    @PostMapping("/multiple")
    public String uploadMultiple(@RequestParam("images") MultipartFile[] files){
        Arrays.stream(files).forEach(file ->{
            logger.info("File name {} ",file.getOriginalFilename());
            logger.info("File type {} ",file.getContentType());
            System.out.println("****************************************");
        });
        return "Handling mulitple files";
    }

    @GetMapping("/serve-image")
    public void serveImageHandler(HttpServletResponse response){
        try{
            InputStream fileInputStream = new FileInputStream("data.jpg");
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(fileInputStream,response.getOutputStream());
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
