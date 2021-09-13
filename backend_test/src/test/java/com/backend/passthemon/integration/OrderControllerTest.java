package com.backend.passthemon.integration;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.consumer.ImgCensor;
import com.backend.passthemon.service.OrderService;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private ImgCensor imgCensor;
    private ObjectMapper objectMapper=new ObjectMapper();
    private ObjectNode objectNode= objectMapper.createObjectNode();
    private TestRestTemplate testRestTemplate=new TestRestTemplate();
    private HttpHeaders headersPost = new HttpHeaders();
    private RestTemplate restTemplate=new RestTemplate();
    @Test
    public void testMakeOther(){
        Integer goodsId=1;
        String url = "http://localhost:8080/order/makeOrder";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId",2);
        paramMap.add("goodsId",1);
        paramMap.add("goodsNum",1);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, httpHeaders);
        String result=testRestTemplate.postForObject(url,httpEntity,String.class);
        JSONObject jsonObject=JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.getString("message"), MsgUtil.ALL_OK_MSG);
    }
    @Test
    public void testCommentAndRateOnOrder(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "*/*");
        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(null , headers);
        String url = "http://localhost:8080/order/commentAndRateOnOrder";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("orderId",1).queryParam("comment"," ").queryParam("rating",3);
        ResponseEntity<JSONObject> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, httpEntity, JSONObject.class);
        Assert.assertEquals(response.getBody().getString("message"),MsgUtil.ALL_OK_MSG);
    }

}
