package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.dao.RoleDAO;
import com.tathao.eshopping.dao.UserDAO;
import com.tathao.eshopping.dao.UserGroupDAO;
import com.tathao.eshopping.model.dto.CustomerDTO;
import com.tathao.eshopping.model.dto.RoleDTO;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.model.dto.UserGroupDTO;
import com.tathao.eshopping.model.entity.CustomerEntity;
import com.tathao.eshopping.model.entity.RoleEntity;
import com.tathao.eshopping.model.entity.UserEntity;
import com.tathao.eshopping.model.entity.UserGroupEntity;
import com.tathao.eshopping.service.CustomerService;
import com.tathao.eshopping.service.UserService;
import com.tathao.eshopping.ultils.CommonUtils;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.GeneratePasswordEncodeUtils;
import com.tathao.eshopping.ultils.mapper.handle.CustomerBeanUtils;
import com.tathao.eshopping.ultils.mapper.handle.RoleBeanUtils;
import com.tathao.eshopping.ultils.mapper.handle.UserBeanUtils;
import com.tathao.eshopping.ultils.mapper.handle.UserGroupBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserGroupDAO userGroupDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private CustomerService customerService;

    @Override
    public UserDTO findById(String userId) {
        UserEntity entity = userDAO.findById(userId);
        return UserBeanUtils.entity2DTO(entity);
    }

    @Override
    public UserDTO findByMail(String email) {
        UserEntity entity = userDAO.findByEmail(email);
        return UserBeanUtils.entity2DTO(entity);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        UserEntity entity = userDAO.findByUserName(userName);
        return UserBeanUtils.entity2DTO(entity);
    }

    @Override
    public UserDTO registerUser(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        UserProfile userProfile = connection.fetchUserProfile();

        String email = userProfile.getEmail();
        UserEntity userEntity = userDAO.findByEmail(email);
        if(userEntity != null) {
            return UserBeanUtils.entity2DTO(userEntity);
        }

        userEntity = new UserEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String userNamePrefix = userProfile.getFirstName().trim().toLowerCase() + "_" + userProfile.getLastName().trim().toLowerCase();

        String userName = userDAO.findAvailableUserName(userNamePrefix);
        userEntity.setUsername(userName);
        userEntity.setCode(CommonUtils.generateCode());
        userEntity.setCreatedDate(now);
        userEntity.setEmail(email);
        userEntity.setFirstName(userProfile.getFirstName());
        userEntity.setLastName(userProfile.getLastName());
        userEntity.setFullName(userProfile.getLastName() + " " + userProfile.getFirstName());
        userEntity.setPhoneNumber("0xxxxxxxxx");
        UserGroupEntity userGroupEntity = userGroupDAO.findByCode(CoreConstants.UserGroup.SHOPPER.getValue());
        userEntity.setUserGroup(userGroupEntity);
        userEntity.setStatus(true);
        RoleEntity role = roleDAO.findByCode(CoreConstants.Role.SHOPPER.getValue());
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(role);
        userEntity.setRoles(roles);
        return UserBeanUtils.entity2DTO(userEntity);
    }

    @Override
    public UserDTO saveOrUpdate(UserDTO userDTO) {
        UserEntity userEntity = UserBeanUtils.dto2Entity(userDTO);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(userEntity.getUserId() != null) {
            userEntity = userDAO.findById(userDTO.getUserId());
            userEntity.setModifiedDate(now);
            if(!StringUtils.isBlank(userDTO.getPassword())) {
                String passwordEncode = GeneratePasswordEncodeUtils.encode(userDTO.getPassword());
                userEntity.setPassword(passwordEncode);
            }
            userEntity = userDAO.update(userEntity);
        } else {
            userEntity.setPassword(GeneratePasswordEncodeUtils.encode(userDTO.getPassword()));
            userEntity.setCreatedDate(now);
            userEntity.setCode(CommonUtils.generateCode());
            userEntity.setStatus(true);
            userEntity.setRoles(null);
            userEntity = userDAO.save(userEntity);
            userEntity.setRoles(RoleBeanUtils.dtos2Entites(userDTO.getRoles()));
            userEntity = userDAO.update(userEntity);
        }
        return UserBeanUtils.entity2DTO(userEntity);
    }

    @Override
    public UserDTO registerCustomer(UserDTO userDTO) {
        //1. add user
        //1.1. add role shopper
        List<RoleDTO> roles = new ArrayList<>();
        RoleEntity roleEntity = roleDAO.findByCode(CoreConstants.Role.SHOPPER.getValue());
        RoleDTO roleDTO = RoleBeanUtils.entity2Dto(roleEntity);
        roles.add(roleDTO);
        userDTO.setRoles(roles);
        //1.2. add user group shopper
        UserGroupEntity userGroupEntity = userGroupDAO.findByCode(CoreConstants.UserGroup.SHOPPER.getValue());
        UserGroupDTO userGroupDTO = UserGroupBeanUtils.entity2DTO(userGroupEntity);
        userDTO.setUserGroup(userGroupDTO);
        userDTO = this.saveOrUpdate(userDTO);
        //2. add customer
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUser(userDTO);
        customerDTO = customerService.saveOrUpdate(customerDTO);
        return userDTO;
    }
}
