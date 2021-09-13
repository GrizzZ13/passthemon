package com.backend.passthemon.constant;

public class RabbitmqConstant {
    //topic交换机的名字
    public static final String TOPIC_EXCHANGE_NAME="topicExchange";
    public static final String QUEUE_GOODS_NAME="topic.a";
    public static final String QUEUE_DEMAND_NAME="topic.b";
    public static final String QUEUE_EDIT_GOODS_NAME="topic.c";
    public static final String QUEUE_EDIT_DEMAND_NAME="topic.d";
    public static final String QUEUE_GOODS_BINDING_KEY="topic.goods";
    public static final String QUEUE_DEMAND_BINDING_KEY="topic.demand";
    public static final String QUEUE_EDIT_GOODS_BINDING_KEY="topic.editGoods";
    public static final String QUEUE_EDIT_DEMAND_BINDING_KEY="topic.editDemand";
}
