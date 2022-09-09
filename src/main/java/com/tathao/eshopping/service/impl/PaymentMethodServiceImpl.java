package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.PaymentMethodDAO;
import com.tathao.eshopping.model.dto.PaymentMethodDTO;
import com.tathao.eshopping.model.entity.PaymentMethodEntity;
import com.tathao.eshopping.service.PaymentMethodService;
import com.tathao.eshopping.ultils.mapper.handle.PaymentMethodBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodDAO paymentMethodDAO;

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause) {
        Object[] result = paymentMethodDAO.findByProperties(properties, sortExpression, sortDirection, offset, limit, whereClause);
        List<PaymentMethodDTO> paymentMethodDTOs = new ArrayList<>();
        for(PaymentMethodEntity paymentMethodEntity : (List<PaymentMethodEntity>)result[1]) {
            paymentMethodDTOs.add(PaymentMethodBeanUtils.entity2DTO(paymentMethodEntity));
        }
        result[1] = paymentMethodDTOs;
        return result;
    }
}
