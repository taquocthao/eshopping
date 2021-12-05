package com.tathao.eshopping.validator;

import com.tathao.eshopping.model.command.ProductCommand;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.model.dto.ProductSkuDimensionDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator extends ApplicationObjectSupport implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductCommand command = (ProductCommand) o;
        trimInput(command);
        checkRequiredInput(command, errors);
        checkExistProduct(command, errors);
    }

    private void trimInput(ProductCommand command) {
        ProductDTO pojo = command.getPojo();
        if(!StringUtils.isBlank(pojo.getCode())) {
            pojo.setCode(pojo.getCode().trim());
        }
        if(!StringUtils.isBlank(pojo.getDescription())) {
            pojo.setDescription(pojo.getDescription());
        }
        if(!StringUtils.isBlank(pojo.getName())) {
            pojo.setName(pojo.getName());
        }
        command.setPojo(pojo);
    }

    private void checkRequiredInput(ProductCommand command, Errors errors) {
        ProductDTO productDTO = command.getPojo();

        // category name
        if(productDTO.getCatGroup() == null || StringUtils.isBlank(productDTO.getCatGroup().getCode())) {
            errors.rejectValue("pojo.catGroup.code", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.catgroup.name")}, "non-empty value required");
        }
        // product name
        if(StringUtils.isBlank(productDTO.getName())) {
            errors.rejectValue("pojo.name", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.product.name")}, "non-empty value required");
        }

        // product image
        if(StringUtils.isBlank(productDTO.getImage())) {
            errors.rejectValue("pojo.image", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.image")}, "none-empty value required");
        }

        // product sku
        if(productDTO.getSku() == null) {
            errors.rejectValue("pojo.sku", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.sku")}, "none-empty value required");
        }

        if(productDTO.getSku() != null) {
            for(int i = 0; i < productDTO.getSku().size(); i++) {
                ProductSkuDTO productSkuDTO = productDTO.getSku().get(i);
                // sku image
                if(StringUtils.isBlank(productSkuDTO.getImage())) {
                    errors.rejectValue("pojo.sku["+ i +"].image", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.image")}, "none-empty value required");
                }
                // sku title
                if(StringUtils.isBlank(productSkuDTO.getTitle())) {
                    errors.rejectValue("pojo.sku["+ i +"].title", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.sku.name")}, "none-empty value required");
                }
                // sku dimension
                if(productSkuDTO.getSkuDimensionDTOs() != null) {
                    for(int j = 0; j < productSkuDTO.getSkuDimensionDTOs().size(); j++) {
                        ProductSkuDimensionDTO skuDimensionDTO = productSkuDTO.getSkuDimensionDTOs().get(j);
                        // size
                        if(StringUtils.isBlank(skuDimensionDTO.getSize())) {
                            errors.rejectValue("pojo.sku["+ i +"].skuDimensionDTOs["+ j +"].size", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.size")}, "none-empty value required");
                        }
                        // original price
                        if(skuDimensionDTO.getOriginalPrice() == null) {
                            errors.rejectValue("pojo.sku["+ i +"].skuDimensionDTOs["+ j +"].originalPrice", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.original-price")}, "none-empty value required");
                        }
                        // sale price
                        if(skuDimensionDTO.getSalePrice() == null) {
                            errors.rejectValue("pojo.sku["+ i +"].skuDimensionDTOs["+ j +"].salePrice", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.sale-price")}, "none-empty value required");
                        }
                    }
                } else {
                    errors.rejectValue("pojo.sku["+ i +"].skuDimensionDTOs", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.sku-dimension")}, "none-empty value required");
                }
            }
        }


        // product description
        if(StringUtils.isBlank(productDTO.getDescription())) {
            errors.rejectValue("pojo.description", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.description")}, "non-empty value required");
        }
    }

    private void checkExistProduct(ProductCommand command, Errors errors) {
    }


}
