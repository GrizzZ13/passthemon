package com.backend.passthemon.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.dto.GoodsDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.HistoryRepository;
import com.backend.passthemon.repository.ImagesRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public JSONObject listGoodsByPages(Integer fetchPage, Integer userId, Integer category, Integer attrition) {
        Page<Goods> goodsPage = goodsRepository.listGoodsByPages(fetchPage, category, attrition);
        List<Goods> goodsList = goodsPage.getContent();
        Integer maxPage = goodsPage.getTotalPages();

//        User user = new User(userId);
//        List<History> historyList = historyRepository.listHistory(user, 0);
//
//        //从此开始的冗长代码用于获取所期望的tag
//        int[] s = new int[7];
//        for(int i = 0 ; i < 7 ; i++) {
//            s[i] = 0;
//        }
//        for(History history : historyList){
//            List<Tag> tags = history.getGoods().getTags();
//            for(Tag tag : tags){
//                if(tag.getTagName() == "服饰装扮"){
//                    s[0]++;
//                    continue;
//                }
//                else{
//                    if(tag.getTagName() == "酒水零食"){
//                        s[1]++;
//                        continue;
//                    }
//                    else{
//                        if(tag.getTagName() == "电子产品"){
//                            s[2]++;
//                            continue;
//                        }
//                        else{
//                            if(tag.getTagName() == "游戏道具"){
//                                s[3]++;
//                                continue;
//                            }
//                            else{
//                                if(tag.getTagName() == "纸质资料"){
//                                    s[4]++;
//                                    continue;
//                                }
//                                else{
//                                    if(tag.getTagName() == "生活用品"){
//                                        s[5]++;
//                                        continue;
//                                    }
//                                    else{
//                                        if(tag.getTagName() == "其他"){
//                                            s[6]++;
//                                            continue;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        int max = 0,  maxIndex = 0;
//        String targetTag = "";
//        for(int i = 0 ; i < 7 ; i++) {
//            if(s[i] > max){
//                max = s[i];
//                maxIndex = i;
//            }
//        }
//        if(maxIndex == 0){
//            targetTag = "服饰装扮";
//        }
//        else{
//            if(maxIndex == 1){
//                targetTag = "酒水零食";
//            }
//            else{
//                if(maxIndex == 2){
//                    targetTag = "电子产品";
//                }
//                else{
//                    if(maxIndex == 3){
//                        targetTag = "游戏道具";
//                    }
//                    else{
//                        if(maxIndex == 4){
//                            targetTag = "纸质资料";
//                        }
//                        else{
//                            if(maxIndex == 5){
//                                targetTag = "生活用品";
//                            }
//                            else{
//                                if(maxIndex == 6){
//                                    targetTag = "其他";
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        List<Goods> tmp = new ArrayList<>();
//        int length = goodsList.size();
//        for(int i = 0; i < length; i++){
//            List<Tag> tags = goodsList.get(i).getTags();
//            for(Tag tag: tags){
//                if(tag.getTagName().equals(targetTag)){
//                    tmp.add(goodsList.get(i));
//                    break;
//                }
//            }
//        }
//        if(tmp.size() < 3) {
//            for (Goods goods : tmp) {
//                goodsList.remove(goods);
//            }
//            for(int i = 0; i < 2; i++) {
//                fetchPage++;
//                if (fetchPage.equals(maxPage)) {
//                    fetchPage = 0;
//                }
//                List<Goods> goodsList2 = goodsRepository.listGoodsByPages(fetchPage).getContent();
//                int length2 = goodsList2.size();
//                for (int j = 0; j < length2; j++) {
//                    List<Tag> tags = goodsList2.get(j).getTags();
//                    for (Tag tag : tags) {
//                        if (tag.getTagName().equals(targetTag)) {
//                            tmp.add(goodsList2.get(j));
//                            break;
//                        }
//                    }
//                }
//                if (tmp.size() >= 3) {
//                    break;
//                }
//            }
//            int size = tmp.size();
//            for(int k = 0; k < 8 - size && k < length; k++){
//                tmp.add(goodsList.get(k));
//            }
//        }
//        else{
//            tmp = goodsList;
//        }

        List<GoodsDto> result = GoodsDto.convert(goodsList);
//        for(GoodsDto goods : result){
//            goods.setImage(imagesRepository.getAllImgByGoodsId(goods.getId()).get(0).getImg());
//        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        jsonObject.put("maxPage", maxPage);
        jsonObject.put("currentPage", fetchPage);

        return jsonObject;
    }

    @Override
    public List<GoodsDto> listMyGoodsByPages(Integer fetchPage, Integer userId) {
        User user = new User(userId);
        List<Goods> list = goodsRepository.listMyGoodsByPages(fetchPage, user);
        List<GoodsDto> result = GoodsDto.convert(list);

        return result;
    }

    @Override
    public List<Goods> searchGoods(String search, Integer fetchPage){
        return goodsRepository.searchGoods(search, fetchPage);
    }

    @Override
    public Goods findGoodsById(Integer goodsId){
        return goodsRepository.findGoodsById(goodsId);
    }
    @Override
    public Integer addGood(Goods goods){return goodsRepository.addGood(goods); }

    @Override
    public  void editGood(GoodInfoDto goodInfoDto){
          goodsRepository.editGood(goodInfoDto);
    }
    @Override
    public void removeGoods(Integer goodsId){
        this.goodsRepository.removeGoods(goodsId);
    }
    @Override
    public Integer getMaxGoodsPage(Integer category, Integer attrition){
        return goodsRepository.getMaxGoodsPage(category, attrition);
    }
    @Override
    public void changeState(Integer state,Integer goodsId){
        goodsRepository.changeState(state,goodsId);
    }
}
