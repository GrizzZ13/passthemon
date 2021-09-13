package com.backend.passthemon.utils.userutils;

import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class UserInfo {
    //姓名
    private String name;
    //信誉
    private Double credit;
    //收藏数目
    private Integer favoriteNum;
    //发布商品数
    private Integer goodsNum;
    //发布需求数
    private Integer demandsNum;
    //购买数目
    private Integer buyNum;
    //出售数目
    private Integer sellNum;
    //性别
    private Integer gender;
    //头像
    private String image;
    //用于查看是否已经关注他人，-1代表未关注，否则代表followId
    private Integer followId=-1;
    public UserInfo(User user){
        this.name=user.getUsername();
        this.credit=user.getCredit();
        this.favoriteNum=user.getFavoriteList().size();
        this.goodsNum=user.getGoodsList().stream().filter((Goods goods)->(!goods.getState().equals(0))).collect(Collectors.toList()).size();
        this.demandsNum=user.getDemandList().stream().filter((Demand demand)->(!demand.getState().equals(0))).collect(Collectors.toList()).size();
        this.buyNum=user.getBuyerList().size();
        this.sellNum=user.getSellerList().size();
        this.gender=user.getGender();
        this.image=user.getImage();
    }
    public UserInfo(){};
}

