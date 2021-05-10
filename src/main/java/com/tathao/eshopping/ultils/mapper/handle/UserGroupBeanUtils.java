package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.UserGroupDTO;
import com.tathao.eshopping.model.entity.UserGroupEntity;

public class UserGroupBeanUtils {

    public static UserGroupDTO entity2DTO(UserGroupEntity entity) {
        if(entity == null) {
            return null;
        }
        UserGroupDTO dto = new UserGroupDTO();
        dto.setCode(entity.getCode());
        dto.setUserGroupId(entity.getUserGroupId());
        dto.setName(entity.getName());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
