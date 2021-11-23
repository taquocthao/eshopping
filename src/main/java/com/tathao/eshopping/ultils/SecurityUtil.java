package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.UserEntity;
import com.tathao.eshopping.model.social.MySocialUserDetails;
import com.tathao.eshopping.ultils.mapper.handle.UserBeanUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private static Logger logger = Logger.getLogger(SecurityUtil.class);

    public static void loginUser(UserEntity userEntity) {
        MySocialUserDetails userDetails = new MySocialUserDetails(userEntity);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void loginUser(UserDTO userDTO) {
        logger.info("SecurityUtil > login user: " + userDTO.getUserId() + ", name: " + userDTO.getFullName());
        UserEntity userEntity = UserBeanUtils.dto2Entity(userDTO);
        loginUser(userEntity);
    }

}
