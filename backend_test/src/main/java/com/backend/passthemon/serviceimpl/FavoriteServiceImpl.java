package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.FavoriteDto;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.FavoriteRepository;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.ImagesRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public List<FavoriteDto> listFavoriteByPage(Integer fetchPage, Integer userId){
        User user = new User(userId);
        List<Favorite> favoriteList = favoriteRepository.listFavoriteByPage(fetchPage, user);
        List<FavoriteDto> result = FavoriteDto.convert(favoriteList);
//        for(FavoriteDto favoriteDto : result){
//            favoriteDto.setImage(imagesRepository.getAllImgByGoodsId(favoriteDto.getId()).get(0).getImg());
//        }

        return result;
    }

    @Override
    public Favorite addFavorite(Integer userId, Integer goodsId){
        User user = userRepository.findUserById(userId);
        Goods goods = goodsRepository.findGoodsById(goodsId);

        return favoriteRepository.addFavorite(user, goods);
    }

    @Override
    public Favorite deleteFavorite(Integer userId, Integer goodsId){
        User user = new User(userId);
        Goods goods = new Goods(goodsId);

        return favoriteRepository.deleteFavorite(user, goods);
    }

    @Override
    public Favorite deleteAllFavorite(Integer userId){
        User user = new User(userId);

        return favoriteRepository.deleteAllFavorite(user);
    }

    @Override
    public Favorite checkFavorite(Integer userId, Integer goodsId){
        User user = new User(userId);
        Goods goods = new Goods(goodsId);

        return favoriteRepository.checkFavorite(user, goods);
    }
}
