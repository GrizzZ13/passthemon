package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.repository.WantsRepository;
import com.backend.passthemon.service.WantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WantsServiceImpl implements WantsService {
    @Autowired
    private WantsRepository wantsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Integer addWants(Integer buyerId, Integer sellerId, Integer goodsId, Integer num){
        User buyer = userRepository.findUserByIdWithCache(buyerId);
        User seller = userRepository.findUserByIdWithCache(sellerId);
        Goods goods = goodsRepository.findGoodsById(goodsId);

        return wantsRepository.addWants(buyer, seller, goods, num);
    }

    @Override
    public Integer deleteWants(Integer buyerId, Integer sellerId, Integer goodsId){
        User buyer = new User(buyerId);
        User seller = new User(sellerId);
        Goods goods = new Goods(goodsId);

        return wantsRepository.deleteWants(buyer, seller, goods);
    }

    @Override
    public List<Wants> sellerListWants(Integer sellerId, Integer fetchPage){
        User seller = new User(sellerId);

        return wantsRepository.sellerListWants(seller, fetchPage);
    }

    @Override
    public Wants checkWants(Integer userId, Integer goodsId){
        User user = new User(userId);
        Goods goods = new Goods(goodsId);

        return wantsRepository.checkWants(user, goods);
    }
}
