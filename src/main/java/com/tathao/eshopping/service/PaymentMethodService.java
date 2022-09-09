package com.tathao.eshopping.service;

import java.util.Map;

public interface PaymentMethodService {

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause);
}
