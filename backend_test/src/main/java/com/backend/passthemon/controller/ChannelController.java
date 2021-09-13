package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.ChannelDto;
import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.ChannelService;
import com.backend.passthemon.service.ChatService;
import com.backend.passthemon.redis.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @Autowired
    ChatService chatService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/channel/getChannels")
    public Msg getChannels(@RequestParam("userid") Integer userid) {
        List<Channel> channels = channelService.getChannelsByAuthor(new User(userid));
        List<ChannelDto> channelDtoList = ChannelDto.convert(channels);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", channelDtoList);
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
    }

    @RequestMapping("/channel/getHistory")
    public Msg getHistoryByOffsetAndPageNumber(@RequestParam("author") Integer author,
                                               @RequestParam("recipient") Integer recipient,
                                               @RequestParam("counts") Integer counts,
                                               @RequestParam("pageNumber") Integer pageNumber){
        log.info("pageNumber : " + pageNumber.toString() + " , counts : " + counts.toString());
        List<Chat> chats = chatService.getChatHistoryByChannelsAndOffset(author, recipient, counts, pageNumber);
        List<ChatDto> chatDtoList = ChatDto.convert(chats);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", chatDtoList);
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
    }

    @RequestMapping("/channel/initHistory")
    public Msg initHistory(@RequestParam("author") Integer author,
                           @RequestParam("recipient") Integer recipient){
        List<ChatDto> chatDtoList = new LinkedList<>();
        getChatDtoList(recipient, author, chatDtoList);
        getChatDtoList(author, recipient, chatDtoList);
        Collections.sort(chatDtoList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", chatDtoList);
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
    }

    private void getChatDtoList(@RequestParam("author") Integer author,
                                @RequestParam("recipient") Integer recipient,
                                List<ChatDto> chatDtoList) {
        // get channel id
        Integer channelId;
        String key = KeyUtil.ChannelKey(author, recipient);
        if (!redisService.exists(key)){
            // search in mysql
            channelId = channelService.getChannelIdByAuthorAndRecipient(author, recipient);
            if(channelId==null) return;
        }
        else{
            channelId = (Integer) redisService.get(key);
        }

        // get chat history
        String chatKey = KeyUtil.ChatKey(channelId);
        List<Object> objectList2 = redisService.range(chatKey, 0, -1);
        for (Object object : objectList2)
            chatDtoList.add((ChatDto) object);
    }
}
