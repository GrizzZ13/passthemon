package com.backend.passthemon.utils.msgutils;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Msg {
    private Integer status;
    private String message;
    private String token;
    private JSONObject data;
}
