package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.FavoriteDao;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {
    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    public List<Favorite> listFavoriteByPage(Integer fetchPage, User user) {
        Pageable pageable = PageRequest.of(fetchPage, 8);
        Page<Favorite> page = favoriteDao.findAllFavoriteByUser(user, pageable);
        List<Favorite> result = page.getContent();

        return result;
    }

    @Override
    public Favorite addFavorite(User user, Goods goods){
        Favorite favorite = new Favorite();
        favorite.setGoods(goods);
        favorite.setUser(user);
        Favorite result = favoriteDao.save(favorite);

        return result;
    }

    @Override
    public Favorite deleteFavorite(User user, Goods goods){
        favoriteDao.deleteByGoodsAndUser(goods, user);
        return null;
    }


    @Override
    public Favorite deleteAllFavorite(User user){
        favoriteDao.deleteByUser(user);
        return null;
    }

    @Override
    public Favorite checkFavorite(User user, Goods goods) {
        return favoriteDao.findFavoriteByGoodsAndUser(goods, user);
    }
}
