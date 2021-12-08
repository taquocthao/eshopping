package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.model.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDAOImpl extends AbstractHibernateDAO<ProductEntity, Long> implements ProductDAO {

    @Override
    public boolean updateStatus(List<Long> ids, boolean active) {
        StringBuilder sql = new StringBuilder();
        sql.append("update {h-schema}product set status = :active ");
        sql.append("where productid in (:ids)");
        Query query = getCurrentSession().createSQLQuery(sql.toString());
        query.setParameter("ids", ids);
        query.setParameter("active", active);
        return query.executeUpdate() > 0;
    }
}
