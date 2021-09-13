package com.backend.passthemon.integration;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.service.HistoryService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HistoryControllerTest {
    @Autowired
    private HistoryService historyService;

    private TestRestTemplate testRestTemplate=new TestRestTemplate();

    @Test
    public void listHistory(){
        Integer fetchPage = 0;
        Integer userId = 4;
        String url = "http://localhost:8080/history/listHistory";
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
    public void listHistoryByTime(){
        Integer userId = 4;
        Integer fetchPage = 0;
        String url = "http://localhost:8080/history/listHistoryByTime";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("fetchPage", fetchPage);
        paramMap.add("userId", userId);
        paramMap.add("startTime", "2021-05-06 00:00:00");
        paramMap.add("endTime", "2021-08-26 23:59:59");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void deleteAllHistory(){
        Integer userId = 1;
        String url = "http://localhost:8080/history/deleteAllHistory";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
}
