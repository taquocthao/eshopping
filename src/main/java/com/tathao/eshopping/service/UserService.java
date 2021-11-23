package com.tathao.eshopping.service;

import com.tathao.eshopping.model.dto.CustomerDTO;
import com.tathao.eshopping.model.dto.UserDTO;
import org.springframework.social.connect.Connection;

public interface UserService {

    UserDTO findById(String userId);

    UserDTO findByMail(String email);

    UserDTO findByUserName(String userName);

    UserDTO registerUser(Connection<?> connection);

    UserDTO saveOrUpdate(UserDTO user);

    UserDTO registerCustomer(UserDTO userDTO);
}
