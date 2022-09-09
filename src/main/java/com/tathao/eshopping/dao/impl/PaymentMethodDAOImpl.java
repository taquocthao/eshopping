package com.tathao.eshopping.dao.impl;

import com.tathao.eshopping.dao.PaymentMethodDAO;
import com.tathao.eshopping.model.entity.PaymentMethodEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentMethodDAOImpl extends AbstractHibernateDAO<PaymentMethodEntity, Long> implements PaymentMethodDAO {

}
