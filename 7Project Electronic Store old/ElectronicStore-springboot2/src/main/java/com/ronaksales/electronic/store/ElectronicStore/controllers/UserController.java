package com.ronaksales.electronic.store.ElectronicStore.controllers;

import com.ronaksales.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.ronaksales.electronic.store.ElectronicStore.dtos.ImageResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UserDto;
import com.ronaksales.electronic.store.ElectronicStore.services.FileService;
import com.ronaksales.electronic.store.ElectronicStore.services.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    // create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Valid
            @RequestBody UserDto userDto,
            @PathVariable("userId") String userId){
        UserDto userDto1 = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(
            @PathVariable("userId") String userId) throws IOException {
        userService.deleteUser(userId);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User is deleted Successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    // get all users
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value="pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pagesize,
            @RequestParam(value="sortBy",defaultValue ="name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue ="asc",required = false) String sortDir){
        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pagesize,sortBy,sortDir),HttpStatus.OK);
    }


    // get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    // get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable("email") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    // search user

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> getUserByKeyword(
            @PathVariable("keywords") String keywords){
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }


    // upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage")MultipartFile image,
            @PathVariable("userId") String userId ) throws IOException {

        // get the image and upload it in the specified folder(imageUploadPath)
        String imageName = fileService.uploadFile(image,imageUploadPath);

        // update the image name for the given user Id
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user,userId);

        // create the Imageresponse
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }


    // serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId,
                               HttpServletResponse response) throws IOException {

        // get the image using the userId
        UserDto user= userService.getUserById(userId);
        logger.info("User Image name : {} ",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath,user.getImageName());

        // create the response one's we got the image
        // user HttpServletResponse copy resource and set the content type as image jpeg
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // copy the source-> resource into Destination-> response as getOutputStream
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
