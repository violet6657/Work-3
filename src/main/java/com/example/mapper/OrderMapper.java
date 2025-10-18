package com.example.mapper;

import JavaBean.order;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderMapper {
    int insertOrder(order order);
    List<order> getAllOrders();
    order getOrderByCode(Integer code);
    int deleteOrderByCode(Integer code);
    int updateOrder(order order);
}