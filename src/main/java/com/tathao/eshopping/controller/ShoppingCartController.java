package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.dto.ShoppingCartDTO;
import com.tathao.eshopping.model.dto.ShoppingCartRequestDTO;
import com.tathao.eshopping.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ShoppingCartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/ajax/shopping-cart/add")
    public ResponseEntity<Object> addToCart(@RequestBody ShoppingCartRequestDTO requestDTO) {
        ShoppingCartDTO shoppingCartDTO = null;
        try {
            shoppingCartDTO = shoppingCartService.addToCart(requestDTO);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(shoppingCartDTO);
    }

}
