package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.OrderOutletDAO;
import com.tathao.eshopping.model.entity.OrderOutletEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Map;

@Repository
public class OrderOutletDAOImpl extends AbstractHibernateDAO<OrderOutletEntity, Long> implements OrderOutletDAO {

    @Override
    public Integer countTotalOrderByStatus(String status) {
        StringBuilder sql = new StringBuilder("select count(*) ");
        sql.append("from {h-schema}orderoutlet o ");
        sql.append("where o.status = :status");
        Query query = getCurrentOrOpenSession().createNativeQuery(sql.toString());
        query.setParameter("status", status);
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
