package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.GoodsDao;
import com.backend.passthemon.dao.mongo.ImagesDao;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.entity.*;
import com.backend.passthemon.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

import static com.backend.passthemon.constant.GoodsConstant.*;

@Repository
public class GoodsRepositoryImpl implements GoodsRepository {
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ImagesDao imagesDao;

    @Override
    public Page<Goods> listGoodsByPages(Integer fetchPage, Integer category, Integer attrition){
        Pageable pageable = PageRequest.of(fetchPage, 8);
        Page<Goods> result;
        if(category == 0 && attrition == 0){
            result = goodsDao.findAllByState(1, pageable);
        }
        else{
            if(category != 0 && attrition == 0){
                result = goodsDao.findAllByStateAndCategory(1, category, pageable);
            }
            else{
                if(category == 0){
                    result = goodsDao.findAllByStateAndAttrition(pageable, 1, attrition);
                }
                else{
                    result = goodsDao.findAllByStateAndAttritionAndCategory(pageable, 1, attrition, category);
                }
            }
        }

        return result;
    }

    @Override
    public List<Goods> listMyGoodsByPages(Integer fetchPage, User user){
        Pageable pageable = PageRequest.of(fetchPage, 8);
        List<Integer> stateList = new ArrayList<>();
        stateList.add(IMAGE_FAILED_AUDIT);
        stateList.add(TEXT_FAILED_AUDIT);
        stateList.add(ON_SALE);
        stateList.add(BEING_AUDITED);
        Page<Goods> goodsPage = goodsDao.findAllByUserAndStateIn(pageable, user, stateList);
        List<Goods> result = goodsPage.getContent();

        return result;
    }

    @Override
    public List<Goods> searchGoods(String search, Integer fetchPage){
        Pageable pageable = PageRequest.of(fetchPage, 8);
        Page<Goods> goodsPage = goodsDao.findAllByNameLikeAndState("%"+search+"%", pageable, 1);
        List<Goods> result = goodsPage.getContent();

        return result;
    }

    @Override
    public Goods findGoodsById(Integer goodsId){
        return goodsDao.findGoodsById(goodsId);
    }
    @Override
    public Goods addGood(Goods goods){
        return goodsDao.saveAndFlush(goods);
    }
    @Override
    public Integer editGood(GoodInfoDto goodInfoDto,Integer userId){
        Goods goods=goodsDao.findGoodsById(goodInfoDto.getGoodId());
        if(goods.getUser().getId().equals(userId)){
        List<String> images=goodInfoDto.getImages();
        goods.setDescription(goodInfoDto.getDescription());
        goods.setName(goodInfoDto.getName());
        goods.setPrice(goodInfoDto.getPrice());
        goods.setInventory(goodInfoDto.getInventory());
        goods.setCategory(goodInfoDto.getCategory());
        goods.setAttrition(goodInfoDto.getAttrition());
        imagesDao.deleteImagesByGoodsId(goods.getId());
        List<String> imageList = new ArrayList<>();
        for(int i=0;i<images.size();i++){
            imageList.add(images.get(i));
        }
        imagesDao.save(new Images(goods.getId(), imageList));

        goodsDao.saveAndFlush(goods);
        return 1;
        } else {
            return -1;
        }
    }
    @Override
    public Integer removeGoods(Integer goodsId,Integer userId){
        Goods goods=goodsDao.findGoodsById(goodsId);
        if(goods.getUser().getId().equals(userId)){
        goods.setState(0);
        goodsDao.saveAndFlush(goods);
        return 1;
        }
        else {
            return -1;
        }
    }
    @Override
    public Integer getMaxGoodsPage(Integer category, Integer attrition){
        Pageable pageable = PageRequest.of(0, 8);
        Page<Goods> result;
        if(category == 0 && attrition == 0){
            result = goodsDao.findAllByState(1, pageable);
        }
        else{
            if(category != 0 && attrition == 0){
                result = goodsDao.findAllByStateAndCategory(1, category, pageable);
            }
            else{
                if(category == 0){
                    result = goodsDao.findAllByStateAndAttrition(pageable, 1, attrition);
                }
                else{
                    result = goodsDao.findAllByStateAndAttritionAndCategory(pageable, 1, attrition, category);
                }
            }
        }
        return result.getTotalPages();
    }

    @Override
    public Integer countGoodsByUserAndStateIsNot(User user, Integer state) {
        return goodsDao.countGoodsByUserAndStateIsNot(user, state);
    }

    @Override
    public void changeState(Integer state,Integer goodsId){
        Goods goods=goodsDao.findGoodsById(goodsId);
        goods.setState(state);
        goodsDao.saveAndFlush(goods);
    }
}
