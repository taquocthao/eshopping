package com.tathao.eshopping.service;

import java.util.Map;

public interface OrderOutletService {

    Object[] findByProperties(Map<String, Object> buildProperties, String sortExpression, String sortDirection, int firstItem, int totalItems, String whereClause);
}
