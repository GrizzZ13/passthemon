package com.backend.passthemon.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.constant.ChatConstant;
import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.service.ChannelService;
import com.backend.passthemon.service.ChatService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.wsutils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@ServerEndpoint("/chat/{userid}")
@Component
public class WebSocketServer {

    public static ChatService chatService;

    public static ChannelService channelService;

    public static UserService userService;

    public static void updateChannelId(Integer userid_1, Integer channelId_1,
                                Integer userid_2, Integer channelId_2){
        try{
            updateChannelIdPrivate(userid_1, channelId_1, userid_2);
            updateChannelIdPrivate(userid_2, channelId_2, userid_1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void updateChannelIdPrivate(Integer userid_1, Integer channelId_1, Integer userid_2) throws IOException {
        if(WebSocketUtil.containsKey(userid_1)){
            // channel-1 : from user1 to user2
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", ChatConstant.CHANNEL);
            jsonObject.put("channelId", channelId_1);
            jsonObject.put("other", userid_2);
            WebSocketUtil.get(userid_1).sendMessage(jsonObject.toJSONString());
        }
    }

    // spring singleton in conflict with websocket multi-object
    // inject service to class
    @Autowired
    public void setChatService(ChatService chatService){
        WebSocketServer.chatService = chatService;
    }

    @Autowired
    public void setChannelService(ChannelService channelService){
        WebSocketServer.channelService = channelService;
    }

    @Autowired
    public void setUserService(UserService userService){
        WebSocketServer.userService = userService;
    }
    private Session session;

    private Integer userid;

    @OnOpen
    public void onOpen(Session session, @PathParam("userid") Integer userid){
        this.session = session;
        this.userid = userid;

        if(WebSocketUtil.containsKey(userid)){
            log.info("[WebSocketServer - onOpen] : key - " + userid + " already exists");
            WebSocketUtil.remove(userid);
            WebSocketUtil.put(userid, this);
        }
        else{
            log.info("[WebSocketServer - onOpen] : key - " + userid + " doesn't exist.");
            WebSocketUtil.put(userid, this);
        }

        try {
            ChatDto chatDto = new ChatDto(0,0,"connected", new Date().toString());
            JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(chatDto);
            sendMessage(jsonObject1.toJSONString());
        }
        catch (IOException e) {
            log.error("[WebSocketServer - onOpen] : caught exception");
        }
    }

    @OnClose
    public void onClose() {
        if(WebSocketUtil.containsKey(userid)){
            WebSocketUtil.remove(userid);
        }
        log.info("[WebSocketServer - onClose] : user offline - " + userid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if(StringUtils.isNotBlank(message)){
            try {
                // 解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);

                Integer author = jsonObject.getInteger("author");
                Integer recipient = jsonObject.getInteger("recipient");
                Integer channelId = jsonObject.getInteger("channelId");
                String content = jsonObject.getString("content");
                Date date;
                ChatDto chatDto;
                if(content==null || content.isEmpty()) return;
                if(channelId==null || channelId < 0){
                    Integer channelId1 = channelService.newChannel(author, recipient);
                    Integer channelId2 = channelService.newChannel(recipient, author);
                    updateChannelId(author, channelId1, recipient, channelId2);
                    channelId = channelId1;
                }
                if(recipient!=null){
                    date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    chatDto = new ChatDto(channelId, author, recipient, content, format.format(date));
                    JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(chatDto);
                    if (WebSocketUtil.containsKey(recipient))
                        WebSocketUtil.get(recipient).sendMessage(jsonObject1.toJSONString());
                    chatService.saveInRedis(chatDto);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[WebSocketServer - onError] : user - "+this.userid+", "+error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
