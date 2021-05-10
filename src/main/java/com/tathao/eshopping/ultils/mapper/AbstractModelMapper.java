package com.tathao.eshopping.ultils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public abstract class AbstractModelMapper<D, E> implements GenericModelMapper<D, E> {

    @Autowired
    private ModelMapper modelMapper;

    private Class<D> dto;
    private Class<E> entity;

    public AbstractModelMapper() {
        this.dto = (Class<D>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entity = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public D entity2DTO(E e) {
        if(e == null) {
            return null;
        }
        return modelMapper.map(e, this.dto);
    }

    @Override
    public E dto2Entity(D d) {
        if(d == null) {
            return null;
        }
        return modelMapper.map(d, this.entity);
    }
}
