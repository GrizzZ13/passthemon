package com.backend.passthemon.integration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.service.WantsService;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WantsControllerTest {
    @Autowired
    private WantsService wantsService;

    private TestRestTemplate testRestTemplate=new TestRestTemplate();

    @Test
    public void addWants(){
        Integer buyerId = 2;
        Integer sellerId = 1;
        Integer goodsId = 1;
        Integer num = 1;
        String url = "http://localhost:8080/wants/addWants";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("buyerId", buyerId);
        paramMap.add("sellerId", sellerId);
        paramMap.add("goodsId", goodsId);
        paramMap.add("num", num);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void deleteWants(){
        Integer buyerId = 2;
        Integer sellerId = 1;
        Integer goodsId = 1;
        String url = "http://localhost:8080/wants/deleteWants";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("buyerId", buyerId);
        paramMap.add("sellerId", sellerId);
        paramMap.add("goodsId", goodsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void checkWants(){
        Integer userId = 4;
        Integer goodsId = 1;
        String url = "http://localhost:8080/wants/checkWants";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId", userId);
        paramMap.add("goodsId", goodsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
}
