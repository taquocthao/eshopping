package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.RoleDTO;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.RoleEntity;
import com.tathao.eshopping.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

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
        dto.setRoles(RoleBeanUtils.entities2Dtos(entity.getRoles()));
        return dto;
    }

    public static UserEntity dto2Entity(UserDTO dto) {
        if(dto == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setStatus(dto.getStatus());
        entity.setUserId(dto.getUserId());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setCode(dto.getCode());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setFullName(dto.getFullName());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUserGroup(UserGroupBeanUtils.dto2Entity(dto.getUserGroup()));
        entity.setRoles(RoleBeanUtils.dtos2Entites(dto.getRoles()));
        return entity;
    }

}
