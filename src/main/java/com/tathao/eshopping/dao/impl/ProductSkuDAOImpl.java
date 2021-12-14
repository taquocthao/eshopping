package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.ProductSkuDAO;
import com.tathao.eshopping.model.entity.ProductSkuEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductSkuDAOImpl extends AbstractHibernateDAO<ProductSkuEntity, Long> implements ProductSkuDAO {

    @Override
    public boolean delete(List<Long> idsDelete) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from {h-schema}productsku ps where ps.productskuid in (:ids)");
        Query query = this.getCurrentSession().createNativeQuery(sql.toString());
        query.setParameter("ids", idsDelete);
        return query.executeUpdate() > 0;
    }
}
