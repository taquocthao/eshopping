package com.tathao.eshopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.ultils.CoreConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/product/{catGroup}/{productCode}/detail.html")
    public ModelAndView viewDetail(@PathVariable("catGroup") String catGroup, @PathVariable("productCode") String productCode) {
        ModelAndView mav = new ModelAndView("/shopper/productDetails");
        try {
            ProductDTO dto = productService.findByCodeAndFetchRelatedProducts(productCode);
            mav.addObject("product", dto);
        } catch (Exception e) {
             logger.error(e.getMessage());
        }
        return mav;
    }

    @RequestMapping(value = "/ajax/product/search.json")
    @ResponseBody
    public Object searchProducts(@RequestParam(value = "query") String query) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", query);
            Object[] resultObject = productService.findByProperties(properties, "name", CoreConstants.SORT_ASC, 0, CoreConstants.TEN_ITEMS);
            map.put("products", resultObject[1]);
        } catch (Exception e) {
            map.put("result", false);
            logger.error(e.getMessage());
        }
        return map;
    }

}
