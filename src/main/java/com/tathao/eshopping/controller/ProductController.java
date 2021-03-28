package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/{catGroup}/{productCode}/detail.html")
    public ModelAndView viewDetail(@PathVariable("catGroup") String catGroup, @PathVariable("productCode") String productCode) {
        ModelAndView mav = new ModelAndView("productDetailShopper");
        ProductDTO dto = new ProductDTO();
        try {
            dto = productService.findByCode(productCode);
        } catch (Exception e) {
             logger.error(e.getMessage());
        }
        mav.addObject("product", dto);
        return mav;
    }

}
