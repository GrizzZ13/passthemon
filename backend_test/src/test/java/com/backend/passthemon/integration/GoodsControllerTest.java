package com.backend.passthemon.integration;

import com.backend.passthemon.producer.GoodsProducer;
import com.backend.passthemon.service.GoodsService;
import com.backend.passthemon.service.HistoryService;
import com.backend.passthemon.service.ImagesService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GoodsControllerTest {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private GoodsProducer goodsProducer;
    private ObjectMapper objectMapper=new ObjectMapper();
    private ObjectNode objectNode= objectMapper.createObjectNode();
    private TestRestTemplate testRestTemplate=new TestRestTemplate();
    private HttpHeaders headersPost = new HttpHeaders();
    @Test
    public void testAddGoods(){
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        String url="http://localhost:8080/goods/addGood";
        objectNode.put("name","测试");
        objectNode.put("inventory",3);
        objectNode.put("price",12.34);
        objectNode.put("description","测试");
        objectNode.put("upload_time","2021-08-29 21:48:23");
        objectNode.put("userId",1);
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(" ");
        objectNode.put("images",jsonArray.toString());
        JSONArray jsonArray1=new JSONArray();
        jsonArray1.add(1);
        jsonArray1.add(-1);
        objectNode.put("labels",jsonArray1.toString());
        HttpEntity<String> request = new HttpEntity<String>(objectNode.toString(),headersPost);
        String result=testRestTemplate.postForObject(url,request,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testEditGoods(){
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        String url="http://localhost:8080/goods/editGood";
        objectNode.put("name","测试");
        objectNode.put("inventory",3);
        objectNode.put("price",12.34);
        objectNode.put("description","测试");
        objectNode.put("upload_time","2021-08-29 21:48:23");
        objectNode.put("goodId",1);
        objectNode.put("userId",1);
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(" ");
        objectNode.put("images",jsonArray.toString());
        JSONArray jsonArray1=new JSONArray();
        jsonArray1.add(1);
        jsonArray1.add(-1);
        objectNode.put("labels",jsonArray1.toString());
        HttpEntity<String> request = new HttpEntity<String>(objectNode.toString(),headersPost);
        String result=testRestTemplate.postForObject(url,request,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void removeGoods(){
        Integer goodsId=1;
        String url = "http://localhost:8080/goods/removeGoods";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodsId",goodsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void getGoodsById(){
        Integer goodsId=1;
        String url = "http://localhost:8080/goods/getGoodsById";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodId",goodsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void listGoodsByPages(){
        Integer fetchPage = 0;
        Integer userId = 1;
        Integer category = 0;
        Integer attrition = 0;
        String url = "http://localhost:8080/goods/listGoodsByPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("fetchPage", fetchPage);
        paramMap.add("userId", userId);
        paramMap.add("category", category);
        paramMap.add("attrition", attrition);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void listMyGoodsByPages(){
        Integer fetchPage = 0;
        Integer userId = 1;
        String url = "http://localhost:8080/goods/listMyGoodsByPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("fetchPage", fetchPage);
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void findGoodsById(){
        Integer goodsId = 1;
        Integer userId = 1;
        String url = "http://localhost:8080/goods/findGoodsById";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodsId", goodsId);
        paramMap.add("userId", userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void searchGoods(){
        String search = "1";
        Integer fetchPage = 0;
        String url = "http://localhost:8080/goods/searchGoods";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("search", search);
        paramMap.add("fetchPage", fetchPage);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void getWanter(){
        Integer goodsId = 1;
        String url = "http://localhost:8080/goods/getWanter";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("goodsId", goodsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void getMaxGoodsPage(){
        Integer category = 0;
        Integer attrition = 0;
        String url = "http://localhost:8080/goods/getMaxGoodsPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("category", category);
        paramMap.add("attrition", attrition);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
}
