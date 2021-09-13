package com.backend.passthemon.integration;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.service.FavoriteService;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FavoriteControllerTest {
    @Autowired
    private FavoriteService favoriteService;

    private TestRestTemplate testRestTemplate=new TestRestTemplate();

    @Test
    public void listFavoriteByPage(){
        Integer fetchPage = 0;
        Integer userId = 1;
        String url = "http://localhost:8080/Favorite/listFavoriteByPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("fetchPage", fetchPage);
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void addFavorite(){
        Integer goodsId = 1;
        Integer userId = 1;
        String url = "http://localhost:8080/Favorite/addFavorite ";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodsId", goodsId);
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void deleteFavorite(){
        Integer goodsId = 1;
        Integer userId = 1;
        String url = "http://localhost:8080/Favorite/deleteFavorite";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodsId", goodsId);
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void deleteAllFavorite(){
        Integer userId = 1;
        String url = "http://localhost:8080/Favorite/deleteAllFavorite";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void checkFavorite(){
        Integer userId = 1;
        Integer goodsId = 1;
        String url = "http://localhost:8080/Favorite/checkFavorite";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodsId", goodsId);
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
}
