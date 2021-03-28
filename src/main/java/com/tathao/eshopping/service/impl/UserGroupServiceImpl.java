package com.tathao.eshopping.service.impl;

import com.tathao.eshopping.model.entity.UserGroupEntity;
import com.tathao.eshopping.service.UserGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {
    public List<UserGroupEntity> findAll() {
        return null;
    }
}
