package com.ronaksales.electronic.store.ElectronicStore.controllers;

//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.ronaksales.electronic.store.ElectronicStore.dtos.JwtRequest;
import com.ronaksales.electronic.store.ElectronicStore.dtos.JwtResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UserDto;
import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.BadApiRequestException;
import com.ronaksales.electronic.store.ElectronicStore.security.JwtHelper;
import com.ronaksales.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;

    @Value("${googleClientId}")
    private String googleClientId;

    @Value("${newPassword}")
    private String newPassword;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails,UserDto.class);
        JwtResponse response = JwtResponse.builder().jwtToken(token).userDto(userDto).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authentication);
        }catch (BadCredentialsException ex){
            throw new BadApiRequestException("Invalid Username or Password !!");
        }
    }


    // This API will return complete information on which user is logged in currently
    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrectUser(Principal principal){
        String name = principal.getName();
        // UserDetailsService loadUserByUsername method will return UserDetails
        // this will return entire information of the user
        // example
//        {
//            "password": "$2a$10$njQhEThTxgRtPpiF70NUGuN/T5jWl7.wyhhPogspYlQpvYjU9fQVu",
//                "username": "ronak",
//                "authorities": [
//            {
//                "authority": "ROLE_ADMIN"
//            }
//],
//            "accountNonExpired": true,
//                "accountNonLocked": true,
//                "credentialsNonExpired": true,
//                "enabled": true
//        }
        UserDto userDto = mapper.map(userDetailsService.loadUserByUsername(name),UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

//    // login with google api
//    @PostMapping("/google")
//    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String,Object> data) throws IOException {
//        // get the id token from request
//        String idToken = data.get("idToken").toString();
//
//        NetHttpTransport netHttpTransport = new NetHttpTransport();
//        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
//
//        // above object will use to make google verifier
//
//        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport,jacksonFactory).setAudience(Collections.singleton(googleClientId));
//
//        // using this verifier we will create google Id token
//        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(),idToken);
//        // get the payload
//        GoogleIdToken.Payload payload = googleIdToken.getPayload();
//        logger.info("Payload: {}",payload);
//
//        // get emailId, etc from payload
//        String email = payload.getEmail();
//
//        User user = null;
//        userService.findUserByEmailOptional(email).orElse(null);
//        if(user == null){
//            // user not found so create a new user
//            user = this.saveUser(email,data.get("name").toString(),data.get("photoUrl").toString());
//        }
//
//        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
//        return jwtResponseResponseEntity;
//
//    }

    private User saveUser(String email, String name, String photoUrl) {
        // create new user
        UserDto newUser = UserDto.builder().name(name)
                .email(email).password(newPassword).imageName(photoUrl).roles(new HashSet<>()).build();

        UserDto userDto = userService.createUser(newUser);

        return this.mapper.map(userDto,User.class);
    }

}


