package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.CustomerAddressDTO;
import com.tathao.eshopping.model.entity.CustomerAddressEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomerAddressBeanUtils {

    public static CustomerAddressEntity dto2Entity(CustomerAddressDTO dto) {
        if(dto == null) {
            return null;
        }
        CustomerAddressEntity entity = new CustomerAddressEntity();
        entity.setCity(dto.getCity());
        entity.setStreetAddress(dto.getStreetAddress());
        entity.setProvincial(dto.getProvincial());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setLongitude(dto.getLongitude());
        entity.setLatitude(dto.getLatitude());
        entity.setDistrict(dto.getDistrict());
        entity.setDefaultAddress(dto.isDefaultAddress());
        entity.setCustomerAddressId(dto.getCustomerAddressId());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }

    public static CustomerAddressDTO entity2Dto(CustomerAddressEntity entity) {
        if(entity == null) {
            return null;
        }
        CustomerAddressDTO dto = new CustomerAddressDTO();
        dto.setCity(entity.getCity());
        dto.setStreetAddress(entity.getStreetAddress());
        dto.setProvincial(entity.getProvincial());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setLongitude(entity.getLongitude());
        dto.setLatitude(entity.getLatitude());
        dto.setDistrict(entity.getDistrict());
        dto.setDefaultAddress(entity.isDefaultAddress());
        dto.setCustomerAddressId(entity.getCustomerAddressId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public static List<CustomerAddressEntity> dtos2Entities(List<CustomerAddressDTO> dtos) {
        if(dtos == null) {
            return null;
        }
        List<CustomerAddressEntity> entities = new ArrayList<>();
        for(CustomerAddressDTO dto : dtos) {
            entities.add(dto2Entity(dto));
        }
        return  entities;
    }

    public static List<CustomerAddressDTO> entities2Dtos(List<CustomerAddressEntity> entities) {
        if(entities == null) {
            return null;
        }
        List<CustomerAddressDTO> dtos = new ArrayList<>();
        for(CustomerAddressEntity entity : entities) {
            dtos.add(entity2Dto(entity));
        }
        return  dtos;
    }
}
