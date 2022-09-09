package com.tathao.eshopping.controller.admin;

import com.tathao.eshopping.service.PaymentMethodService;
import com.tathao.eshopping.ultils.CoreConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentMethodController {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private PaymentMethodService paymentMethodService;

    @RequestMapping(value = "/ajax/admin/payment/get-all.json")
    @ResponseBody
    public Object getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("result", false);
        try {
            Map<String, Object> properties = new HashMap<>();
            String whereClause = "Active = true";
            Object[] resultObject = paymentMethodService.findByProperties(properties, "priority", CoreConstants.SORT_ASC, 0, CoreConstants.MILION_ITEMS, whereClause);
            if(Integer.parseInt(resultObject[0].toString()) > 0) {
                map.put("result", true);
            }
            map.put("payments", resultObject[1]);
        } catch (Exception e) {
            logger.error("POSITION ProductSkuDimensionController - method search - error: ", e);
        }
        return map;
    }

}
