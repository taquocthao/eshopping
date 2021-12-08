package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.CatGroupDAO;
import com.tathao.eshopping.dao.ProductDAO;
import com.tathao.eshopping.dao.ProductSkuDAO;
import com.tathao.eshopping.dao.ProductSkuDimensionDAO;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.dto.ProductSkuDimensionDTO;
import com.tathao.eshopping.model.entity.*;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.ultils.CommonUtils;
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
    @Autowired
    private CatGroupDAO catGroupDAO;

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
        CatGroupEntity catGroupEntity = catGroupDAO.findEqualUnique("code", productDTO.getCatGroup().getCode());
        productDB.setCatGroup(catGroupEntity);
        productDB.setDescription(productDTO.getDescription());
        productDB.setImage(productDTO.getImage());
        productDB.setName(productDTO.getName());
        productDB.setStatus(productDTO.getStatus());
        List<ProductSkuEntity> skuEntities = new ArrayList<>();
        for (ProductSkuDTO skuDTO : productDTO.getSku()) {
            ProductSkuEntity skuEntity = saveOrUpdate(skuDTO);
            skuEntities.add(skuEntity);
        }
        productDB.setProductSkus(skuEntities);
        productDB = productDAO.update(productDB);
        return ProductBeanUtils.entity2DTO(productDB);
    }

    private ProductSkuEntity saveOrUpdate(ProductSkuDTO skuDTO) {
        ProductSkuEntity productSkuEntity = null;
        if(skuDTO == null) {
            return productSkuEntity;
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(skuDTO.getProductSkuId() == null) { // insert
            productSkuEntity = ProductSkuBeanUtils.dto2Entity(skuDTO);
            productSkuEntity.setCreatedDate(now);
            productSkuEntity.setSkuCode(CommonUtils.generateCode());

        } else { // update
            productSkuEntity = productSkuDAO.findById(skuDTO.getProductSkuId());
            productSkuEntity.setModifiedDate(now);
            productSkuEntity.setTitle(skuDTO.getTitle());
            productSkuEntity.setStatus(skuDTO.getStatus());
            productSkuEntity.setImage(skuDTO.getImage());
        }

        List<ProductSkuDimensionEntity> skuDimensionEntities = new ArrayList<>();
        for(ProductSkuDimensionDTO dimensionDTO : skuDTO.getSkuDimensionDTOs()) {
            ProductSkuDimensionEntity dimensionEntity = saveOrUpdate(dimensionDTO);
            skuDimensionEntities.add(dimensionEntity);
        }
        productSkuEntity.setSkuDimensions(skuDimensionEntities);
        return productSkuEntity;
    }

    private ProductSkuDimensionEntity saveOrUpdate(ProductSkuDimensionDTO dimensionDTO) {
        ProductSkuDimensionEntity dimensionEntity = null;
        if(dimensionDTO == null) {
            return dimensionEntity;
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(dimensionDTO.getProductSkuDimensionId() == null) { // insert
            dimensionEntity = ProductSkuDimensionBeanUtils.dto2Entity(dimensionDTO);
            dimensionEntity.setCreatedDate(now);
            dimensionEntity.setActive(true);
            String code = CommonUtils.generateCode();
            dimensionEntity.setBarCode(code);
            dimensionEntity.setCode(code);
        } else { // update
            dimensionEntity = productSkuDimensionDAO.findById(dimensionDTO.getProductSkuDimensionId());
            dimensionEntity.setModifiedDate(now);
            dimensionEntity.setActive(dimensionDTO.getActive());
            dimensionEntity.setDepth(dimensionDTO.getDepth());
            dimensionEntity.setHeight(dimensionDTO.getHeight());
            dimensionEntity.setOriginalPrice(dimensionDTO.getOriginalPrice());
            dimensionEntity.setSalePrice(dimensionDTO.getSalePrice());
            dimensionEntity.setSize(dimensionDTO.getSize());
        }
        return dimensionEntity;
    }

    @Override
    public ProductDTO add(ProductDTO pojo) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ProductEntity productEntity = new ProductEntity();
        productEntity.setStatus(pojo.getStatus());
        productEntity.setName(pojo.getName());
        productEntity.setImage(pojo.getImage());
        productEntity.setDescription(pojo.getDescription());
        productEntity.setCreatedDate(now);
        productEntity.setCode(CommonUtils.generateCode());
        CatGroupEntity catGroupEntity = catGroupDAO.findEqualUnique("code", pojo.getCatGroup().getCode());
        productEntity.setCatGroup(catGroupEntity);
        List<ProductSkuEntity> productSkus = new ArrayList<>();
        Double lowestPrice = 0D;
        Double highestPrice = 0D;
        for(ProductSkuDTO skuDTO : pojo.getSku()) {
            ProductSkuEntity skuEntity = ProductSkuBeanUtils.dto2Entity(skuDTO);
            skuEntity.setCreatedDate(now);
            skuEntity.setSkuCode(CommonUtils.generateCode());
            // sku dimension
            List<ProductSkuDimensionEntity> skuDimensionEntities = new ArrayList<>();
            for(ProductSkuDimensionDTO dimensionDTO : skuDTO.getSkuDimensionDTOs()) {
                ProductSkuDimensionEntity skuDimensionEntity = ProductSkuDimensionBeanUtils.dto2Entity(dimensionDTO);
                String code = CommonUtils.generateCode();
                skuDimensionEntity.setCreatedDate(now);
                skuDimensionEntity.setCode(code);
                skuDimensionEntity.setBarCode(code);
                skuDimensionEntity.setSku(skuEntity);
                skuDimensionEntities.add(skuDimensionEntity);

                if(lowestPrice == 0D || lowestPrice > dimensionDTO.getSalePrice()) {
                    lowestPrice = dimensionDTO.getSalePrice();
                } else if(lowestPrice < dimensionDTO.getSalePrice()) {
                    highestPrice = dimensionDTO.getSalePrice();
                }

            }
            skuEntity.setSkuDimensions(skuDimensionEntities);
            skuEntity.setProduct(productEntity);
            productSkus.add(skuEntity);
        }
        productEntity.setProductSkus(productSkus);
        ProductReferencePriceEntity referencePriceEntity = new ProductReferencePriceEntity();
        referencePriceEntity.setHighestPrice(highestPrice);
        referencePriceEntity.setLowestPrice(lowestPrice);
        referencePriceEntity.setCreatedDate(now);
        referencePriceEntity.setProduct(productEntity);
        productEntity.setReferencePrice(referencePriceEntity);
        productEntity = productDAO.save(productEntity);

        return ProductBeanUtils.entity2DTO(productEntity);
    }

    @Override
    public boolean updateStatus(List<Long> ids, boolean active) {
        boolean result = false;
        if(ids != null && ids.size() > 0) {
            return productDAO.updateStatus(ids, active);
        }
        return result;
    }
}
