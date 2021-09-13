package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface FavoriteDao extends JpaRepository<Favorite, Integer> {
    Page<Favorite> findAllFavoriteByUser(User user, Pageable pageable);

    @Modifying
    @Transactional
    Integer deleteByGoodsAndUser(Goods goods, User user);

    Favorite findFavoriteByGoodsAndUser(Goods goods, User user);
    Integer countFavoritesByUser(User user);

    @Modifying
    @Transactional
    void deleteAllByUser(User user);
}
