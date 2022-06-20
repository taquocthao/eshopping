package com.tathao.eshopping.controller;

import com.tathao.eshopping.service.ProductSkuDimensionService;
import com.tathao.eshopping.ultils.CoreConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductSkuDimensionController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ProductSkuDimensionService productSkuDimensionService;

    @RequestMapping(value = "/ajax/admin/sku/dimension/search.json")
    @ResponseBody
    public Object search(@RequestParam(value = "query") String query) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", false);
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("sku.product.name", query);
            String whereClause = "1 = 1";
            Object[] resultObject = productSkuDimensionService.findByProperties(properties, "sku.product.name", CoreConstants.SORT_ASC, 0, CoreConstants.TEN_ITEMS, whereClause);
            if(Integer.parseInt(resultObject[0].toString()) > 0) {
                map.put("result", true);
            }
            map.put("products", resultObject[1]);
        } catch (Exception e) {
            logger.error("POSITION ProductSkuDimensionController - method search - error: ", e);
        }
        return map;
    }
}
