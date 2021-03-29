package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.CustomerDTO;
import com.tathao.eshopping.model.entity.CustomerEntity;

public class CustomerBeanUtils {

    public static CustomerDTO entity2DTO(CustomerEntity entity) {
        if(entity == null) {
            return null;
        }
        CustomerDTO dto = new CustomerDTO();
        dto.setActive(entity.isActive());
        dto.setBeforeLastLogin(entity.getBeforeLastLogin());
        dto.setUser(UserBeanUtils.entity2DTO(entity.getUser()));
        dto.setLastLogin(entity.getLastLogin());
        dto.setCustomerId(entity.getCustomerId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setBirthday(entity.getBirthday());

        return dto;
    }

}
