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
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ProductEntity productEntity = new ProductEntity();
        productEntity.setStatus(pojo.getStatus());
        productEntity.setName(pojo.getName());
        productEntity.setImage(pojo.getImage());
        productEntity.setDescription(pojo.getDescription());
        productEntity.setCreatedDate(now);
        productEntity.setCode(CommonUtils.generateCode());
//        ProductReferencePriceEntity referencePriceEntity = new ProductReferencePriceEntity();
//        productEntity.setReferencePrice(referencePriceEntity);
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
        productEntity = productDAO.save(productEntity);
//
//        ProductReferencePriceEntity referencePriceEntity = new ProductReferencePriceEntity();
//        referencePriceEntity.setHighestPrice(highestPrice);
//        referencePriceEntity.setLowestPrice(lowestPrice);
//        referencePriceEntity.setCreatedDate(now);
//        referencePriceEntity.setProduct(productEntity);

        return ProductBeanUtils.entity2DTO(productEntity);
    }
}
