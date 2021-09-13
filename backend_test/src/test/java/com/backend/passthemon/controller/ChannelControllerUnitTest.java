package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.constant.OrderConstant;
import com.backend.passthemon.dto.ChannelDto;
import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.redis.RedisService;
import com.backend.passthemon.service.ChannelService;
import com.backend.passthemon.service.ChatService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ChannelController.class)
public class ChannelControllerUnitTest {
    @MockBean
    private ChannelService channelService;
    @MockBean
    private ChatService chatService;
    @MockBean
    private RedisService redisService;

    @Autowired
    private MockMvc mvc;

    private Integer userId = 1, author = 1,
            recipient = 1, counts = 2, pageNumber = 1, channelId = 1, type = 1;

    private String content = "content", date = "2021-9-11";

    private User user = new User(userId);

    private List<Channel> channels = new ArrayList<>();

    private List<Chat> chats = new ArrayList<>();

    private List<ChatDto> chatDtoList = new LinkedList<>();

    private List<Object> objectList = new ArrayList<>();

    private Object object = new ChatDto(channelId, author, recipient, content, date, type);

    @Test
    public void getChannels() {
        given(this.channelService.getChannelsByAuthor(user)).willReturn(channels);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", ChannelDto.convert(channels));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get(
                    "/channel/getChannels?userid=" + userId.toString()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getHistoryByOffsetAndPageNumber() {
        given(chatService.getChatHistoryByChannelsAndOffset(author, recipient, counts, pageNumber)).willReturn(chats);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", ChatDto.convert(chats));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get(
                    "/channel/getHistory?author=" + author.toString() + "&recipient="
            + recipient.toString() + "&counts=" + counts.toString() + "&pageNumber=" + pageNumber.toString()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void initHistory() {
        String key = KeyUtil.ChannelKey(author, recipient);
        given(redisService.exists(key)).willReturn(false);
        given(channelService.getChannelIdByAuthorAndRecipient(author, recipient)).willReturn(null);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", chatDtoList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get(
                    "/channel/initHistory?author=" + author.toString() + "&recipient=" + recipient.toString()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String chatKey = KeyUtil.ChatKey(channelId);
        objectList.add(object);

        given(redisService.exists(key)).willReturn(true);
        given(redisService.get(key)).willReturn(channelId);
        given(redisService.range(chatKey, 0, -1)).willReturn(objectList);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", chatDtoList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get(
                    "/channel/initHistory?author=" + author.toString() + "&recipient=" + recipient.toString()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
