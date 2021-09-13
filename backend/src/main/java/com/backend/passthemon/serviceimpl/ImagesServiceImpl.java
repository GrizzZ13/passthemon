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
    public Integer addImg(List<String> imgList, Integer goodsId){
        return imagesRepository.addImg(imgList, goodsId);
    }

    @Override
    public List<String> getAllImgByGoodsId(Integer goodsId){
        List<String> result = imagesRepository.getAllImgByGoodsId(goodsId);

        return result;
    }

    @Override
    public String getAllCoverForThisPage(Integer goodsId){
        String result = imagesRepository.getAllCoverForThisPage(goodsId);

        return result;
    }
}
