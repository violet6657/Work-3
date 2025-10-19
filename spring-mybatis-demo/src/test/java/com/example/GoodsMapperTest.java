package com.example;

import JavaBean.good;
import com.example.mapper.GoodsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GoodsMapper 测试类，用于验证 GoodsMapper 接口的功能
 */
@SpringBootTest
@Transactional
class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    void testInsertGoods() {
        good goods = new good(null, "测试商品", 10.0);
        int result = goodsMapper.insertGoods(goods);
        assertTrue(result > 0);
    }

    @Test
    void testGetAllGoods() {
        goodsMapper.insertGoods(new good(null, "测试商品", 15.0));
        List<good> goodsList = goodsMapper.getAllGoods();
        assertNotNull(goodsList);
        assertFalse(goodsList.isEmpty());
    }

    @Test
    void testGetGoodsByCode() {
        good goods = new good(null, "测试商品", 20.0);
        goodsMapper.insertGoods(goods);
        good retrieved = goodsMapper.getGoodsByCode(goods.getGoodsCode());
        assertNotNull(retrieved);
        assertEquals("测试商品", retrieved.getGoodsName());
    }

    @Test
    void testDeleteGoodsByCode() {
        good goods = new good(null, "测试商品", 25.0);
        goodsMapper.insertGoods(goods);
        int result = goodsMapper.deleteGoodsByCode(goods.getGoodsCode());
        assertTrue(result > 0);
        good retrieved = goodsMapper.getGoodsByCode(goods.getGoodsCode());
        assertNull(retrieved);
    }

    @Test
    void testUpdateGoods() {
        good goods = new good(null, "测试商品", 30.0);
        goodsMapper.insertGoods(goods);
        goods.setGoodsName("更新商品");
        goods.setGoodsPrice(35.0);
        int result = goodsMapper.updateGoods(goods);
        assertTrue(result > 0);
        good updated = goodsMapper.getGoodsByCode(goods.getGoodsCode());
        assertEquals("更新商品", updated.getGoodsName());
        assertEquals(35.0, updated.getGoodsPrice());
    }

    @Test
    void testGetAllGoodsCode() {
        goodsMapper.insertGoods(new good(null, "测试商品", 40.0));
        List<Integer> codes = goodsMapper.getAllGoodsCode();
        assertNotNull(codes);
        assertFalse(codes.isEmpty());
    }

    @Test
    void testGetGoodsByPage() {
        for (int i = 1; i <= 3; i++) {
            goodsMapper.insertGoods(new good(null, "商品" + i, 10.0 * i));
        }
        List<good> goodsList = goodsMapper.getGoodsByPage(0, 2);
        assertNotNull(goodsList);
        assertTrue(goodsList.size() <= 2);
    }

    @Test
    void testCountOrdersGoodsByGoodsCode() {
        Integer count = goodsMapper.countOrdersGoodsByGoodsCode(999);
        assertNotNull(count);
        assertEquals(0, count);
    }

    @Test
    void testGetTotalGoodsCount() {
        goodsMapper.insertGoods(new good(null, "测试商品", 50.0));
        Integer count = goodsMapper.getTotalGoodsCount();
        assertNotNull(count);
        assertTrue(count > 0);
    }
}