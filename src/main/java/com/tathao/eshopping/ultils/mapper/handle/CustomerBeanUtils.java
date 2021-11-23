package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.CustomerAddressDTO;
import com.tathao.eshopping.model.dto.CustomerDTO;
import com.tathao.eshopping.model.entity.CustomerAddressEntity;
import com.tathao.eshopping.model.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

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
        dto.setCode(entity.getCode());
        dto.setCustomerAddress(CustomerAddressBeanUtils.entities2Dtos(entity.getCustomerAddress()));
        return dto;
    }

    public static CustomerEntity dto2Entity(CustomerDTO dto) {
        if(dto == null) {
            return null;
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setActive(dto.isActive());
        entity.setLastLogin(dto.getLastLogin());
        entity.setCustomerId(dto.getCustomerId());
        entity.setCustomerAddress(CustomerAddressBeanUtils.dtos2Entities(dto.getCustomerAddress()));
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCode(dto.getCode());
        entity.setBirthday(dto.getBirthday());
        entity.setBeforeLastLogin(dto.getBeforeLastLogin());
        entity.setUser(UserBeanUtils.dto2Entity(dto.getUser()));
        return entity;
    }

}
