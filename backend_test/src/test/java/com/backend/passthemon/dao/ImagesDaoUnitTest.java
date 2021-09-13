package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Images;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImagesDaoUnitTest {
    @Autowired
    private ImagesDao imagesDao;

    @Test
    void findByGoodsIdAndAndDisplayOrder() {
        String testImages = "1";
        Integer goodsId = 99999999;
        Images images = new Images(testImages, goodsId, 1);
        imagesDao.save(images);
        Assert.assertEquals(testImages, imagesDao.findByGoodsIdAndAndDisplayOrder(goodsId, 1).getImg());
    }

    @Test
    void findAllByGoodsId() {
        String testImages = "1";
        Integer goodsId = 99999998;
        /* 先删除数据库中所有goodsId的商品 */
        imagesDao.deleteAllByGoodsId(goodsId);

        Images images = new Images(testImages, goodsId, 1);
        List<Images> imagesList = new ArrayList<>();
        imagesList.add(images);
        imagesDao.save(images);
        Assert.assertEquals(imagesList, imagesDao.findAllByGoodsId(goodsId));
    }

    @Test
    void deleteAllByGoodsId() {
        String testImages = "1";
        Integer goodsId = 99999997;
        Images images = new Images(testImages, goodsId, 1);
        imagesDao.save(images);
        Integer expect = 1;
        Assert.assertEquals(expect, imagesDao.deleteAllByGoodsId(goodsId));
    }
}
