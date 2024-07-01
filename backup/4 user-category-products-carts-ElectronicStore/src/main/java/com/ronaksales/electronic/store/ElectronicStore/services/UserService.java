package com.ronaksales.electronic.store.ElectronicStore.services;

import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ronaksales.electronic.store.ElectronicStore.dtos.UserDto;
import com.ronaksales.electronic.store.ElectronicStore.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    // create
    UserDto createUser(UserDto user);

    // update
    UserDto updateUser(UserDto userDto, String userId);

    // delete
    void deleteUser(String userId) throws IOException;

    // get all users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get single user by id
    UserDto getUserById(String userId);

    // get single user by email
    UserDto getUserByEmail(String email);

    // search user by name
    List<UserDto> searchUser(String keyword);

    // other user specific features
}
