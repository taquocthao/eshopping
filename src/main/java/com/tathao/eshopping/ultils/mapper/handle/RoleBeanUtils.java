package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.RoleDTO;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;

public class RoleBeanUtils {

    public static RoleEntity dto2Entity(RoleDTO dto) {
        if(dto == null) {
            return null;
        }
        RoleEntity entity = new RoleEntity();
        entity.setCode(dto.getCode());
        entity.setUser(null);
        entity.setRoleId(dto.getRoleId());
        entity.setName(dto.getName());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }

    public static List<RoleEntity> dtos2Entites(List<RoleDTO> dtos) {
        if(dtos == null) {
            return null;
        }
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (RoleDTO dto : dtos) {
            roleEntities.add(dto2Entity(dto));
        }
        return roleEntities;
    }

    public static RoleDTO entity2Dto(RoleEntity entity) {
        if(entity == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO();
        dto.setCode(entity.getCode());
        dto.setUser(null);
        dto.setRoleId(entity.getRoleId());
        dto.setName(entity.getName());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public static List<RoleDTO> entities2Dtos(List<RoleEntity> entities) {
        if(entities == null) {
            return null;
        }
        List<RoleDTO> dtos = new ArrayList<>();
        for(RoleEntity entity : entities) {
            dtos.add(entity2Dto(entity));
        }
        return dtos;
    }

}
