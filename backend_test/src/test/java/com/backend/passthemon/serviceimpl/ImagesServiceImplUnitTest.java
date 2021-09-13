package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Images;
import com.backend.passthemon.repository.ImagesRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

class ImagesServiceImplUnitTest {
    @Mock
    private ImagesRepository imagesRepository;

    @InjectMocks
    private ImagesServiceImpl imagesService;

    private List<Images> imagesList;

    private Integer goodsId = 1, displayOrder = 1;

    private String img = "img";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(imagesRepository.getAllImgByGoodsId(goodsId)).thenReturn(imagesList);
        Mockito.when(imagesRepository.addImg(img, goodsId, displayOrder)).thenReturn(goodsId);
    }

    @Test
    void addImg() {
        Integer result = imagesService.addImg(img, goodsId, displayOrder);
        Assert.assertEquals(result, goodsId);
        Mockito.verify(imagesRepository).addImg(img, goodsId, displayOrder);
    }

    @Test
    void getAllImgByGoodsId() {
        List<Images> result = imagesService.getAllImgByGoodsId(goodsId);
        Assert.assertEquals(imagesList, result);
        Mockito.verify(imagesRepository).getAllImgByGoodsId(goodsId);
    }
}
