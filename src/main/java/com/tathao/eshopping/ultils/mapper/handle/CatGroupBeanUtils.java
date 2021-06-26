package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.CatGroupDTO;
import com.tathao.eshopping.model.entity.CatGroupEntity;

public class CatGroupBeanUtils {

    public static CatGroupEntity dto2Entity(CatGroupDTO dto) {
        CatGroupEntity entity = new CatGroupEntity();
        entity.setCatGroupId(dto.getCatGroupId());
        entity.setCode(dto.getCode());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());
        entity.setStatus(dto.getStatus());
        if(dto.getCatGroupId() != null) {
            CatGroupEntity parent = new CatGroupEntity();
            parent.setCatGroupId(dto.getCatGroupId());
            entity.setParent(parent);
        }
        entity.setName(dto.getName());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }

    public static CatGroupDTO entity2DTO(CatGroupEntity entity) {
        if(entity == null) {
            return null;
        }
        CatGroupDTO dto = new CatGroupDTO();
        dto.setCatGroupId(entity.getCatGroupId());
        dto.setCode(entity.getCode());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());
        if(entity.getParent() != null) {
            CatGroupDTO parent = entity2DTO(entity.getParent());
            dto.setParent(parent);
        }
        dto.setName(entity.getName());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

}
