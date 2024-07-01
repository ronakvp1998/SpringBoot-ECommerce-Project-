package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ronaksales.electronic.store.ElectronicStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user with give Emailid not found"));

        // user is child of UserDetails so we can return it
        return user;
    }
}
