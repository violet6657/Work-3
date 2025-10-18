package com.example.mapper;

import JavaBean.good_order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OGMapper {
        int insertOG(good_order good_order);
        String getGoodsNameByOrderCode(Integer order_code);

}
