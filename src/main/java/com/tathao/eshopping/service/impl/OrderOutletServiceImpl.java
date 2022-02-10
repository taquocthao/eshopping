package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.OrderOutletDAO;
import com.tathao.eshopping.service.OrderOutletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class OrderOutletServiceImpl implements OrderOutletService {

    private static Logger logger = LoggerFactory.getLogger(OrderOutletServiceImpl.class);

    @Autowired
    private OrderOutletDAO orderOutletDAO;

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int totalItems, String whereClause) {
        Object[] result = orderOutletDAO.findByPropertiesFetchTotalOrder(properties, sortExpression, sortDirection, firstItem, totalItems, whereClause);
        logger.info("find list object - result: " + Arrays.toString(result));
        return result;
    }
}
