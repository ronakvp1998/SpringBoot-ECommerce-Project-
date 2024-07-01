package com.ronaksales.electronic.store.ElectronicStore.controllers;

import com.ronaksales.electronic.store.ElectronicStore.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

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

}


