package com.tathao.eshopping.ultils.mapper.handle;

import com.tathao.eshopping.model.dto.PaymentMethodDTO;
import com.tathao.eshopping.model.entity.PaymentMethodEntity;

public class PaymentMethodBeanUtils {

    public static PaymentMethodEntity dto2Entity(PaymentMethodDTO dto) {
        if(dto == null) {
            return null;
        }
        PaymentMethodEntity entity = new PaymentMethodEntity();
        entity.setCode(dto.getCode());
        entity.setActive(dto.getActive());
        entity.setCreateDate(dto.getCreateDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setPaymentMethodId(dto.getPaymentMethodId());
        entity.setPriority(dto.getPriority());
        entity.setValue(dto.getValue());
        return entity;
    }

    public static PaymentMethodDTO entity2DTO(PaymentMethodEntity entity) {
        if(entity == null) {
            return null;
        }
        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setCode(entity.getCode());
        dto.setActive(entity.getActive());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setPaymentMethodId(entity.getPaymentMethodId());
        dto.setPriority(entity.getPriority());
        dto.setValue(entity.getValue());
        return dto;
    }

}
