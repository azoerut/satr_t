package org.example.controller;

import org.example.pojo.dto.UserDTO;
import org.example.pojo.entity.User;
import org.example.pojo.vo.UserVO;
import org.example.properties.JwtProperties;
import org.example.result.Result;
import org.example.service.impl.UserServiceImpl;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO);



        Map<String,Object> claims = new HashMap<>();
        /////////////////////////////////////////
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        /////////////////////////////////////////
        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims))
                .build();

        return Result.success(userVO);
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return Result.success();
    }

    /**
     * 这个测试没有授权的会被拦截
     * @return
     */
    @GetMapping("/test")
    public String test() {
        return "hello";
    }

}
