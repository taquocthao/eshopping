package com.tathao.eshopping.ultils.mapper;

public interface GenericModelMapper<D, E> {
    public D entity2DTO(E e);
    public E dto2Entity(D d);
}
