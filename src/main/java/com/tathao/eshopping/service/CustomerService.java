package com.tathao.eshopping.service;

import com.tathao.eshopping.model.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO register(CustomerDTO dto);

    CustomerDTO saveOrUpdate(CustomerDTO dto);
}
