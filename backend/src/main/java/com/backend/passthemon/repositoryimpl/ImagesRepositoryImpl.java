package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.mongo.ImagesDao;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagesRepositoryImpl implements ImagesRepository {
    @Autowired
    private ImagesDao imagesDao;

    @Override
    public Integer addImg(List<String> imgList, Integer goodsId){
        List<String> imageList = new ArrayList<>();
        for(int i=0;i<imgList.size();i++){
            imageList.add(imgList.get(i));
        }
        Images result = imagesDao.save(new Images(goodsId, imageList));

        return result.getGoodsId();
    }

    @Override
    public List<String> getAllImgByGoodsId(Integer goodsId){
        Images images = imagesDao.findImagesByGoodsId(goodsId);

        return images.getImageList();
    }

    @Override
    public String getAllCoverForThisPage(Integer goodsId){
        Images images = imagesDao.findImagesByGoodsId(goodsId);

        return images.getImageList().get(0);
    }
}
