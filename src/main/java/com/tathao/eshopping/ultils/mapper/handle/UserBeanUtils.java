package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.UserEntity;

public class UserBeanUtils {

    public static UserDTO entity2DTO(UserEntity entity) {
        if(entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setUsername(entity.getUsername());
        dto.setUserGroup(UserGroupBeanUtils.entity2DTO(entity.getUserGroup()));
        dto.setStatus(entity.getStatus());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPassword(entity.getPassword());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setLastName(entity.getLastName());
        dto.setFullName(entity.getFullName());
        dto.setFirstName(entity.getFirstName());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCode(entity.getCode());
        return dto;
    }

}
