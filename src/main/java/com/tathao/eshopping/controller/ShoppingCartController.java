package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.dto.ShoppingCartDTO;
import com.tathao.eshopping.model.dto.ShoppingCartRequestDTO;
import com.tathao.eshopping.service.ShoppingCartService;
import com.tathao.eshopping.validator.ShoppingCartValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ShoppingCartController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartValidator shoppingCartValidator;

    @RequestMapping(value = "/ajax/shopping-cart/add-to-cart.html")
    public ResponseEntity<Object> addToCart(@ModelAttribute ShoppingCartRequestDTO requestDTO, BindingResult result) {
        try {
            // validate input
            shoppingCartValidator.validate(requestDTO, result);
            if(!result.hasErrors()) {
                // perform action
                ShoppingCartDTO shoppingCartDTO = shoppingCartService.addToCart(requestDTO);
                return ResponseEntity.ok(shoppingCartDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/shopping-cart/details.html")
    public ModelAndView cartDetails() {
        ModelAndView mav = new ModelAndView("/shopper/cartDetails");
        return mav;
    }

}
