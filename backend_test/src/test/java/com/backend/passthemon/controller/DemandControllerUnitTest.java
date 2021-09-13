package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.AllDemandDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.producer.DemandProducer;
import com.backend.passthemon.service.DemandService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DemandController.class)
class DemandControllerUnitTest {
    @MockBean
    DemandService demandService;

    @MockBean
    UserService userService;

    @MockBean
    DemandProducer demandProducer;

    @Autowired
    private MockMvc mvc;

    private List<Demand> demandList = new LinkedList<>();
    private final Demand demand = new Demand();

    @Test
    void addDemand() {
        demand.setId(1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        JSONObject value = JSONObject.parseObject(JSONObject.toJSONString(demand));
        try{
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject);
            mvc.perform(MockMvcRequestBuilders.post("/demand/addDemand").content(value.toString()).contentType("application/json"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void listAllDemandsByPage() {
        List<Demand> demandList = new ArrayList<>();
        Integer fetchPage = 0;
        Integer category = 0;
        Integer attrition = 0;
        Demand demand = new Demand();
        demand.setId(1);
        demand.setName("wo");
        demand.setNum(1);
        demand.setIdealPrice(BigDecimal.valueOf(1));
        demand.setDescription("www");
        demand.setUpLoadTime(Timestamp.valueOf("2021-08-12 12:01:01"));
        demand.setState(1);
        User user = new User(1);
        user.setImage("121");
        user.setUsername("wowoow");
        demand.setUser(user);;
        demandList.add(demand);

        /* 给出预测行为 */
        given(this.demandService.listAllDemandsByPage(fetchPage, category, attrition)).willReturn(demandList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", AllDemandDto.convert(demandList));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/demand/listAllDemandsByPage/?fetchPage=" + fetchPage
            + "&category=" + category + "&attrition=" + attrition))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(null));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void listOnesDemandsByPage() {
        Integer fetchPage = 0, userId = 1;

        /* 给出预测行为 */
        given(this.demandService.listOnesDemandsByPage(fetchPage, userId)).willReturn(demandList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", AllDemandDto.convert(demandList));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/demand/listOnesDemandsByPage/?fetchPage=" + fetchPage
                    + "&userId=" + userId))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void getDemandById() {
        Integer demandId = 1;

        /* 给出预测行为 */
        given(this.demandService.getDemandById(demandId)).willReturn(demand);

        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(demand));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/demand/getDemandById/?demandId=" + demandId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };

    }

    @Test
    void editDemand() {
        demand.setId(1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        JSONObject value = JSONObject.parseObject(JSONObject.toJSONString(demand));
        try{
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject);
            mvc.perform(MockMvcRequestBuilders.post("/demand/editDemand").content(value.toString()).contentType("application/json"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void removeDemand(){
        Mockito.doNothing().when(demandService).removeDemand(Mockito.anyInt());
        Integer demandId=1;
        try{
            mvc.perform(MockMvcRequestBuilders.get("/demand/removeDemand?demandId="+demandId));
            Mockito.verify(demandService,Mockito.times(1)).removeDemand(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
