package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.exceptions.BadApiRequestException;
import com.ronaksales.electronic.store.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFileName = file.getOriginalFilename();
        logger.info("Filename: {}",originalFileName);
        // generate random file name
        String filename = UUID.randomUUID().toString();
        // get the extension of the file .png .jpg etc
        String extension = originalFileName.substring((originalFileName.lastIndexOf(".")));
        String fileNameWithExtension = filename + extension;
//        String fullPathWithFileName = path + File.separator+fileNameWithExtension;
        String fullPathWithFileName = path+fileNameWithExtension;

        logger.info("full image path {}",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){
            logger.info("file extension is {} ",extension);
            File folder = new File(path);
            if(!folder.exists()){
                //create folder and sub folders
                folder.mkdirs();
            }
            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        }else{
            throw new BadApiRequestException("File with this " + extension + " is not allowed !!");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        // create the full path
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }


}
