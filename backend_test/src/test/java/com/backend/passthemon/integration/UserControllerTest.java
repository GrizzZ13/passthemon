package com.backend.passthemon.integration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.service.EmailService;
import com.backend.passthemon.service.FollowService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.service.VerificationService;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private FollowService followService;
    private ObjectMapper objectMapper=new ObjectMapper();
    private ObjectNode objectNode= objectMapper.createObjectNode();
    private TestRestTemplate testRestTemplate=new TestRestTemplate();
    private HttpHeaders headersPost = new HttpHeaders();
    @Test
    public void testGetUserByUserId(){
        Integer userId=1;
        String url = "http://localhost:8080/user/getUserByUserId";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId",userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testGetUserInfoByUserId(){
        Integer userId=2;
        String url = "http://localhost:8080/user/getUserInfoByUserId";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId",userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testGetOtherInfo(){
        Integer userId=2;
        String url = "http://localhost:8080/user/getOtherInfo";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("otherId",2);
        paramMap.add("userId",3);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testGetUserNameById(){
        Integer userId=2;
        String url = "http://localhost:8080/user/getUserNameById";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId",userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testEditUser(){
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        String url="http://localhost:8080/user/submitChange";
        objectNode.put("name","测试");
        objectNode.put("userId",2);
        objectNode.put("gender",1);
        objectNode.put("phone","1234567892");
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(" ");
        objectNode.put("image",jsonArray.toString());
        HttpEntity<String> request = new HttpEntity<String>(objectNode.toString(),headersPost);
        String result=testRestTemplate.postForObject(url,request,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"),MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testFollowOther(){
        Integer userId=2;
        String url = "http://localhost:8080/user/followOther";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("otherId",2);
        paramMap.add("userId",3);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testUnfollowOther(){
        Integer userId=2;
        String url = "http://localhost:8080/user/unFollowOther";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("followId",1);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testGetUserByPage(){
        Integer userId=2;
        String url = "http://localhost:8080/user/getUsersByPage";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId",userId);
        paramMap.add("fetchPage",1);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }

}
