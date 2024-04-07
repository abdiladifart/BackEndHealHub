package com.example.backendhealhub.service;

import com.example.backendhealhub.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO registerUser(UserDTO userDTO);

    UserDTO loginUser(String email, String password);
}
