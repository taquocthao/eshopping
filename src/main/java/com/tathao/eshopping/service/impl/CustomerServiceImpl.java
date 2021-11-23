package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.CustomerDAO;
import com.tathao.eshopping.dao.RoleDAO;
import com.tathao.eshopping.dao.UserGroupDAO;
import com.tathao.eshopping.model.dto.CustomerDTO;
import com.tathao.eshopping.model.dto.RoleDTO;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.entity.CustomerEntity;
import com.tathao.eshopping.model.entity.RoleEntity;
import com.tathao.eshopping.model.entity.UserEntity;
import com.tathao.eshopping.model.entity.UserGroupEntity;
import com.tathao.eshopping.service.CustomerService;
import com.tathao.eshopping.service.UserService;
import com.tathao.eshopping.ultils.CommonUtils;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.mapper.handle.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserGroupDAO userGroupDAO;

    @Override
    public CustomerDTO register(CustomerDTO customerDTO) {
        //1. add user
        UserDTO userDTO = customerDTO.getUser();
        //1.1. add role shopper
        List<RoleDTO> roles = new ArrayList<>();
        RoleEntity roleEntity = roleDAO.findByCode(CoreConstants.Role.SHOPPER.getValue());
        RoleDTO roleDTO = RoleBeanUtils.entity2Dto(roleEntity);
        roles.add(roleDTO);
        userDTO.setRoles(roles);
        //1.2. add user group shopper
        UserGroupEntity userGroupEntity = userGroupDAO.findByCode(CoreConstants.UserGroup.SHOPPER.getValue());
        userDTO.setUserGroup(UserGroupBeanUtils.entity2DTO(userGroupEntity));
        userDTO = userService.saveOrUpdate(userDTO);
        //2. add customer
        customerDTO.setUser(userDTO);
        CustomerEntity customerEntity = CustomerBeanUtils.dto2Entity(customerDTO);
        customerDTO = this.saveOrUpdate(CustomerBeanUtils.entity2DTO(customerEntity));
        return customerDTO;
    }

    @Override
    public CustomerDTO saveOrUpdate(CustomerDTO dto) {
        CustomerEntity customerEntity = CustomerBeanUtils.dto2Entity(dto);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(dto.getCustomerId() != null) {
            customerEntity = customerDAO.findById(dto.getCustomerId());
            customerEntity.setUser(UserBeanUtils.dto2Entity(dto.getUser()));
            customerEntity.setCustomerAddress(CustomerAddressBeanUtils.dtos2Entities(dto.getCustomerAddress()));
            customerEntity = customerDAO.update(customerEntity);
        } else {
            customerEntity.setCode(CommonUtils.generateCode());
            customerEntity.setCustomerAddress(null);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(dto.getUser().getUserId());
            customerEntity.setUser(userEntity);
            customerEntity.setCreatedDate(now);
            customerEntity.setActive(true);
            customerEntity = customerDAO.save(customerEntity);
        }
        return CustomerBeanUtils.entity2DTO(customerEntity);
    }

}
