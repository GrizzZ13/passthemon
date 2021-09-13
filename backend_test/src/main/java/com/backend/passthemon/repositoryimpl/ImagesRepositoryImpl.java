package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.ImagesDao;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImagesRepositoryImpl implements ImagesRepository {
    @Autowired
    private ImagesDao imagesDao;

    @Override
    public Integer addImg(String img, Integer goodsId, Integer displayOrder){
        Images images = new Images(img, goodsId, displayOrder);
        Images result = imagesDao.save(images);

        return result.getGoodsId();
    }

    @Override
    public List<Images> getAllImgByGoodsId(Integer goodsId){
        List<Images> result = imagesDao.findAllByGoodsId(goodsId);

        return result;
    }
}
