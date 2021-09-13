package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.ImagesDao;
import com.backend.passthemon.entity.Images;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class ImagesRepositoryImplUnitTest {
    @Mock
    private ImagesDao imagesDao;

    @InjectMocks
    private ImagesRepositoryImpl imagesRepository;

    private List<Images> imagesList = new ArrayList<>();

    private String img = "img";

    private Integer goodsId = 1, displayOrder = 1;

    private Images images = new Images(img, goodsId, displayOrder);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(imagesDao.findAllByGoodsId(goodsId)).thenReturn(imagesList);
    }

    @Test
    void getAllImgByGoodsId() {
        List<Images> result = imagesRepository.getAllImgByGoodsId(goodsId);
        Assert.assertEquals(imagesList, result);
        Mockito.verify(imagesDao).findAllByGoodsId(goodsId);
    }

    @Test
    void addImg() {
        Mockito.when(imagesDao.save(images)).thenReturn(images);
        Assert.assertEquals(imagesRepository.addImg(img, goodsId, displayOrder).toString(),
                String.valueOf(goodsId));
        Mockito.verify(imagesDao).save(images);
    }
}
