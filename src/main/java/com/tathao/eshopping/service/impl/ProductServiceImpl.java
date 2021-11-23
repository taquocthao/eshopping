package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.dao.ProductSkuDAO;
import com.tathao.eshopping.dao.ProductSkuDimensionDAO;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.dto.ProductSkuDimensionDTO;
import com.tathao.eshopping.model.entity.CatGroupEntity;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.model.entity.ProductSkuDimensionEntity;
import com.tathao.eshopping.model.entity.ProductSkuEntity;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.ultils.mapper.handle.ProductBeanUtils;
import com.tathao.eshopping.ultils.mapper.handle.ProductSkuBeanUtils;
import com.tathao.eshopping.ultils.mapper.handle.ProductSkuDimensionBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ProductSkuDAO productSkuDAO;
    @Autowired
    private ProductSkuDimensionDAO productSkuDimensionDAO;

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        return this.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, "1 = 1");
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems, String whereClause) {
        Object[] result = productDAO.findByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for(ProductEntity entity : (List<ProductEntity>)result[1]) {
            products.add(ProductBeanUtils.entity2DTO(entity));
        }
        result[1] = products;
        return result;
    }

    @Override
    public ProductDTO findByCode(String code) {
        ProductEntity productEntity = productDAO.findEqualUnique("code", code);
        return ProductBeanUtils.entity2DTO(productEntity);
    }

    @Override
    public ProductDTO findByCodeAndFetchRelatedProducts(String code) {
        ProductEntity productEntity = productDAO.findEqualUnique("code", code);
        // Get related products
        Map<String, Object> properties = new HashMap<>();
        properties.put("catGroup.catGroupId", productEntity.getCatGroup().getCatGroupId());
        Object[] relatedProducts = productDAO.findByProperties(properties, "createdDate", "1", null, 5);
        return ProductBeanUtils.entity2DTOAndFetchRelatedProducts(productEntity, (List<ProductEntity>) relatedProducts[1]);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ProductEntity productDB = productDAO.findById(productDTO.getProductId());
        productDB.setModifiedDate(now);
        productDB.setBrandId(productDTO.getBrandId());
        CatGroupEntity catGroupEntity = new CatGroupEntity();
        catGroupEntity.setCatGroupId(productDTO.getCatGroup().getCatGroupId());
        productDB.setCatGroup(catGroupEntity);
        productDB.setDescription(productDTO.getDescription());
        productDB.setImage(productDTO.getImage());
        productDB.setName(productDTO.getName());
        productDB.setStatus(productDTO.getStatus());
        //update sku
        for (ProductSkuDTO skuDTO : productDTO.getSku()) {
            ProductSkuEntity productSkuEntity = ProductSkuBeanUtils.dto2Entity(skuDTO);
            productSkuEntity.setModifiedDate(now);
            //update dimension
            for(ProductSkuDimensionDTO dimensionDTO : skuDTO.getSkuDimensionDTOs()) {
                ProductSkuDimensionEntity dimensionEntity = ProductSkuDimensionBeanUtils.dto2Entity(dimensionDTO);
                dimensionEntity.setModifiedDate(now);
                productSkuDimensionDAO.update(dimensionEntity);
            }
            productSkuDAO.update(productSkuEntity);
        }
        productDB = productDAO.update(productDB);
        return ProductBeanUtils.entity2DTO(productDB);
    }

    @Override
    public ProductDTO add(ProductDTO pojo) {
        return null;
    }
}
