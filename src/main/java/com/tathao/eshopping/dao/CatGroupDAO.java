package com.tathao.eshopping.dao;

import com.tathao.eshopping.model.entity.CatGroupEntity;

import java.util.List;

public interface CatGroupDAO extends GenericDAO<CatGroupEntity, Long> {

    List<CatGroupEntity> findParents();

    Boolean updateStatus(List<Long> ids, Boolean status);

    List<CatGroupEntity> findAllExcludeParent();
}
