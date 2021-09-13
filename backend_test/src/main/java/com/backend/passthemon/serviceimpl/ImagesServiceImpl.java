package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Images;
import com.backend.passthemon.repository.ImagesRepository;
import com.backend.passthemon.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {
    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public Integer addImg(String img, Integer goodsId, Integer displayOrder){
        return imagesRepository.addImg(img,goodsId,displayOrder);
    }

    @Override
    public List<Images> getAllImgByGoodsId(Integer goodsId){
        List<Images> result = imagesRepository.getAllImgByGoodsId(goodsId);

        return result;
    }
}
