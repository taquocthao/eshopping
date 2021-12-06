package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.CatGroupDAO;
import com.tathao.eshopping.model.dto.CatGroupDTO;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.entity.CatGroupEntity;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.service.CatGroupService;
import com.tathao.eshopping.ultils.mapper.handle.CatGroupBeanUtils;
import com.tathao.eshopping.ultils.mapper.handle.ProductBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CatGroupServiceImpl implements CatGroupService {

    @Autowired
    private CatGroupDAO catGroupDAO;

    @Override
    public List<CatGroupDTO> findAll4Admin() {
        List<CatGroupEntity> entities = catGroupDAO.findAll();
        return entities.stream().map(entity -> CatGroupBeanUtils.entity2DTO(entity)).collect(Collectors.toList());
    }

    @Override
    public List<CatGroupDTO> findAllExcludeParent() {
        List<CatGroupEntity> entities = catGroupDAO.findAllExcludeParent();
        return entities.stream().map(entity -> CatGroupBeanUtils.entity2DTO(entity)).collect(Collectors.toList());
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems, String whereClause) {
        Object[] result = catGroupDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        List<CatGroupDTO> catGroupDTOS = new ArrayList<CatGroupDTO>();
        for(CatGroupEntity entity : (List<CatGroupEntity>)result[1]) {
            catGroupDTOS.add(CatGroupBeanUtils.entity2DTO(entity));
        }
        result[1] = catGroupDTOS;
        return result;
    }

    @Override
    public CatGroupDTO findByCode(String code) {
        CatGroupEntity entity = catGroupDAO.findEqualUnique("code", code);
        return CatGroupBeanUtils.entity2DTO(entity);
    }

    @Override
    public List<CatGroupDTO> findParentCatGroups() {
        List<CatGroupEntity> entities = catGroupDAO.findParents();
        return entities.stream().map(CatGroupBeanUtils::entity2DTO).collect(Collectors.toList());
    }

    @Override
    public CatGroupDTO saveOrUpdate(CatGroupDTO catGroup) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        CatGroupEntity catGroupEntity = CatGroupBeanUtils.dto2Entity(catGroup);

        if(catGroup.getCatGroupId() != null) { // update
            // get from db
            CatGroupEntity dbCatGroupEntity = catGroupDAO.findById(catGroup.getCatGroupId());
            catGroupEntity.setCatGroupId(dbCatGroupEntity.getCatGroupId());
            catGroupEntity.setCreatedDate(dbCatGroupEntity.getCreatedDate());
            // get from new input
            if(catGroup.getParent() != null && catGroup.getParent().getCode()  != null) {
                CatGroupEntity parent = catGroupDAO.findEqualUnique("code", catGroup.getParent().getCode());
                catGroupEntity.setParent(parent);
            }
            catGroupEntity.setModifiedDate(now);

            dbCatGroupEntity = catGroupEntity;

            catGroupEntity = catGroupDAO.update(dbCatGroupEntity);
        } else { // add
            // parent catgroup
            if(catGroup.getParent() != null && catGroup.getParent().getCode()  != null) {
                CatGroupEntity parent = catGroupDAO.findEqualUnique("code", catGroup.getParent().getCode());
                catGroupEntity.setParent(parent);
            }
            catGroupEntity.setCreatedDate(now);
            catGroupEntity.setModifiedDate(now);
            catGroupEntity= catGroupDAO.save(catGroupEntity);
        }
        return CatGroupBeanUtils.entity2DTO(catGroupEntity);
    }

    @Override
    public Boolean updateStatus(List<Long> ids, Boolean status) {
        Boolean result = false;
        if(ids != null && ids.size() > 0) {
            return catGroupDAO.updateStatus(ids, status);
        }
        return result;
    }
}
