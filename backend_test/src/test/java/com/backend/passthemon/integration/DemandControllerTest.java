package com.backend.passthemon.integration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.producer.DemandProducer;
import com.backend.passthemon.service.DemandService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class DemandControllerTest {
    @Autowired
    DemandService demandService;

    @Autowired
    UserService userService;

    @Autowired
    DemandProducer demandProducer;
    private ObjectMapper objectMapper=new ObjectMapper();
    private ObjectNode objectNode= objectMapper.createObjectNode();
    private TestRestTemplate testRestTemplate=new TestRestTemplate();
    private HttpHeaders headersPost = new HttpHeaders();
    @Test
    public void testAddDemand(){
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        String url="http://localhost:8080/demand/addDemand";
        objectNode.put("name","测试");
        objectNode.put("idealPrice",12.34);
        objectNode.put("description","测试");
        objectNode.put("upload_time","2021-08-29 21:48:23");
        objectNode.put("userId",1);
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(1);
        jsonArray.add(-1);
        objectNode.put("labels",jsonArray.toString());
        HttpEntity<String> request = new HttpEntity<String>(objectNode.toString(),headersPost);
        String result=testRestTemplate.postForObject(url,request,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testEditDemand(){
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        String url="http://localhost:8080/demand/editDemand";
        objectNode.put("name","测试");
        objectNode.put("idealPrice",12.34);
        objectNode.put("description","测试");
        objectNode.put("demandId",1);
        objectNode.put("upload_time","2021-08-29 21:48:23");
        objectNode.put("userId",1);
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(1);
        jsonArray.add(-1);
        objectNode.put("labels",jsonArray.toString());
        HttpEntity<String> request = new HttpEntity<String>(objectNode.toString(),headersPost);
        String result=testRestTemplate.postForObject(url,request,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testGetDemandById(){
        Integer demandId=1;
        String url = "http://localhost:8080/demand/getDemandById";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("demandId",demandId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testListAllDemandsByPage(){
        Integer fetchPage = 0;
        Integer category = 0;
        Integer attrition = 0;
        String url = "http://localhost:8080/demand/listAllDemandsByPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("fetchPage",fetchPage);
        paramMap.add("category",category);
        paramMap.add("attrition",attrition);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testListOnesDemandsByPage(){
        Integer fetchPage = 0;
        Integer userId=1;
        String url = "http://localhost:8080/demand/listOnesDemandsByPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId",userId);
        paramMap.add("fetchPage",fetchPage);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
}
