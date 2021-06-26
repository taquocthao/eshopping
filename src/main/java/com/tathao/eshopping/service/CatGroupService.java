package com.tathao.eshopping.service;

import com.tathao.eshopping.model.dto.CatGroupDTO;

import java.util.List;
import java.util.Map;

public interface CatGroupService {

    List<CatGroupDTO> findAll4Admin();

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems, String whereClause);

    CatGroupDTO findByCode(String code);

    List<CatGroupDTO> findParentCatGroups();

    CatGroupDTO saveOrUpdate(CatGroupDTO catGroup);
}
