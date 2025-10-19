package com.example.mapper;

import JavaBean.good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {
    int insertGoods(good goods);
    List<good> getAllGoods();
    good getGoodsByCode(Integer code);
    int deleteGoodsByCode(Integer code);
    int updateGoods(good goods);
    List<Integer>getAllGoodsCode();
    List<good> getGoodsByPage(@Param("offset") Integer i, @Param("pageSize") Integer pageSize);
    Integer countOrdersGoodsByGoodsCode(Integer code);
    Integer getTotalGoodsCount();
}