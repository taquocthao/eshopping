package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.CatGroupDAO;
import com.tathao.eshopping.model.entity.CatGroupEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CatGroupDAOImpl extends AbstractHibernateDAO<CatGroupEntity, Long> implements CatGroupDAO {

    @Override
    public List<CatGroupEntity> findParents() {
        StringBuilder sql = new StringBuilder("FROM CatGroupEntity c where c.parent is null");
        Query query = getCurrentSession().createQuery(sql.toString());
        return query.getResultList();
    }
}
