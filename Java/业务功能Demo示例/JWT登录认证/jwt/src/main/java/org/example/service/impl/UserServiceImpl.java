package org.example.service.impl;

import org.example.exception.AccountNotFoundException;
import org.example.exception.UserAlreadyExistException;
import org.example.pojo.entity.User;
import org.example.pojo.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.properties.JwtProperties;
import org.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public User login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new AccountNotFoundException("密码错误");
        }
        return user;
    }

    @Override
    public String register(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("用户已存在");
        }
        userMapper.insert(user);
        return "注册成功";
    }
}
