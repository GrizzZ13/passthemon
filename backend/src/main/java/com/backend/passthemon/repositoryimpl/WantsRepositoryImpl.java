package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.WantsDao;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.repository.WantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WantsRepositoryImpl implements WantsRepository {
    @Autowired
    private WantsDao wantsDao;

    @Override
    public Integer addWants(User buyer, User seller, Goods goods, Integer num){
        Wants wants = new Wants();
        wants.setBuyer(buyer);
        wants.setSeller(seller);
        wants.setGoods(goods);
        wants.setNum(num);
        Wants result = wantsDao.saveAndFlush(wants);

        return result.getId();
    }

    @Override
    public Integer deleteWants(User buyer, User seller, Goods goods){
        return wantsDao.deleteWantsByBuyerAndGoodsAndSeller(buyer, goods, seller);
    }

    @Override
    public List<Wants> sellerListWants(User seller, Integer fetchPage){
        Pageable pageable = PageRequest.of(fetchPage, 8);
        Page<Wants> wantsPage = wantsDao.findAllBySeller(seller, pageable);
        List<Wants> result = wantsPage.getContent();

        return result;
    }

    @Override
    public Wants checkWants(User user, Goods goods){
        return wantsDao.findWantsByBuyerAndGoods(user, goods);
    }
}
