package com.ronaksales.electronic.store.ElectronicStore.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    // saves the File and returns the File name
    public String uploadFile(MultipartFile file, String path) throws IOException;

    //  return the inputStream present in the path and name
    InputStream getResource(String path,String name) throws FileNotFoundException;

}
