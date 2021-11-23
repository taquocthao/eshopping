package com.tathao.eshopping.signup;

import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.service.UserService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class MyConnectionSignUp implements ConnectionSignUp {

    private UserService userService;

    public MyConnectionSignUp(UserService userService) {
        this.userService = userService;
    }

    // After login Social.
    // This method is called to create a USER_ACCOUNTS record
    // if it does not exists.
    @Override
    public String execute(Connection<?> connection) {
        UserDTO userDTO = userService.registerUser(connection);
        return userDTO.getUserId();
    }

}
