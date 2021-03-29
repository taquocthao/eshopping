package com.tathao.eshopping.service;

import com.tathao.eshopping.model.dto.ShoppingCartDTO;
import com.tathao.eshopping.model.dto.ShoppingCartRequestDTO;

public interface ShoppingCartService {

    ShoppingCartDTO addToCart(ShoppingCartRequestDTO requestDTO);

}
