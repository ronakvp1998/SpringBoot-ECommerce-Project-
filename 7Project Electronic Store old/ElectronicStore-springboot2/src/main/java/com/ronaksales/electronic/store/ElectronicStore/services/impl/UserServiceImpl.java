package com.ronaksales.electronic.store.ElectronicStore.services.impl;

import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UserDto;
import com.ronaksales.electronic.store.ElectronicStore.entities.Role;
import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import com.ronaksales.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ronaksales.electronic.store.ElectronicStore.helper.Helper;
import com.ronaksales.electronic.store.ElectronicStore.repositories.RoleRepository;
import com.ronaksales.electronic.store.ElectronicStore.repositories.UserRepository;
import com.ronaksales.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${normal.role.id}")
    private String nomalRoleId;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        // generate unique id in String formate
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        // encoding password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // dto to entity
        User user = dtoToEntity(userDto);
        // fetch role of noramal user and set it to user
        Role role = roleRepository.findById(nomalRoleId).get();
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);

        // entity to dto
        UserDto newUserDto = entityToDto(savedUser);
        return newUserDto;
        
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not foudn with given id"));

        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        // Encoding the password
//        user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setImageName(userDto.getImageName());

        // save data
        User updatedUser = userRepository.save(user);

        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        // delete user profile image -> images/user/abc.png
        String fullPath = imagePath + user.getImageName();
        // remove the file
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(NoSuchFileException ex){
            logger.info("User image not found in folder");
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        user.getRoles().clear();
        // delete user
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        // sorting object
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
//      pageable object
        // page will start from 1 for pageNumber-1
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
//        List<User> users = page.getContent();
//        // mapping
//        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
//        // create custom pageable response
//        PageableResponse<UserDto> response = new PageableResponse<>();
//        response.setContent(dtoList);
//        response.setPageNumber(page.getNumber());
//        response.setPageSize(page.getSize());
//        response.setTotalElements(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());

        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given emailId"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        if(users.isEmpty()){
            throw new ResourceNotFoundException("User with keyword not found");
        }
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    private UserDto entityToDto(User savedUser) {

//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
//
//        return userDto;


        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();
//
//        return user;

        return mapper.map(userDto,User.class);
    }

}
