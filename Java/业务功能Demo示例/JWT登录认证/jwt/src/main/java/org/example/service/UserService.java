package org.example.service;

import org.example.pojo.dto.UserDTO;
import org.example.pojo.entity.User;

public interface UserService {

    User login(UserDTO userDTO);

    String register(UserDTO userDTO);
}
