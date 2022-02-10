package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.OrderOutletDAO;
import com.tathao.eshopping.model.enumerate.OrderOutletEnum;
import com.tathao.eshopping.service.OrderOutletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class OrderOutletServiceImpl implements OrderOutletService {

    private static final Logger logger = LoggerFactory.getLogger(OrderOutletServiceImpl.class);

    @Autowired
    private OrderOutletDAO orderOutletDAO;

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int totalItems, String whereClause) {
        Object[] result = new Object[8];

        Object[] listOrder = orderOutletDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, totalItems, whereClause);
        logger.info("find list orderoutlet - result: " + Arrays.toString(result));
        Integer totalOrderWaitingConfirm = orderOutletDAO.countTotalOrderByStatus(OrderOutletEnum.WAITING_CONFIRM.getValue());
        logger.info("total order waiting confirm: " + totalOrderWaitingConfirm);
        Integer totalOrderWaitingPicking = orderOutletDAO.countTotalOrderByStatus(OrderOutletEnum.WAITING_PICKING.getValue());
        logger.info("total order waiting picking: " + totalOrderWaitingPicking);
        Integer totalOrderDelivering = orderOutletDAO.countTotalOrderByStatus(OrderOutletEnum.DELIVERING.getValue());
        logger.info("total order delivering: " + totalOrderDelivering);
        Integer totalOrderSuccess = orderOutletDAO.countTotalOrderByStatus(OrderOutletEnum.SUCCESS.getValue());
        logger.info("total order success: " + totalOrderSuccess);
        Integer totalOrderCancel = orderOutletDAO.countTotalOrderByStatus(OrderOutletEnum.CANCEL.getValue());
        logger.info("total order cancel: " + totalOrderCancel);
        Integer totalOrderReturn = orderOutletDAO.countTotalOrderByStatus(OrderOutletEnum.RETURN.getValue());
        logger.info("total order return: " + totalOrderReturn);

        result[0] = listOrder[0];
        result[1] = listOrder[1];
        result[2] = totalOrderWaitingConfirm;
        result[3] = totalOrderWaitingPicking;
        result[4] = totalOrderDelivering;
        result[5] = totalOrderSuccess;
        result[6] = totalOrderCancel;
        result[7] = totalOrderReturn;
        return result;
    }
}
