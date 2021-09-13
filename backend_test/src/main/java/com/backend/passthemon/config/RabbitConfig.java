package com.backend.passthemon.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.backend.passthemon.constant.RabbitmqConstant.*;

@Configuration
public class RabbitConfig {
    //定义Goods队列
    @Bean
    public Queue topicA(){
        return new Queue(QUEUE_GOODS_NAME);
    }

    //定义Demand队列
    @Bean
    public Queue topicB(){ return new Queue(QUEUE_DEMAND_NAME);}

    //定义EditGoods队列
    @Bean
    public Queue topicC(){ return new Queue(QUEUE_EDIT_GOODS_NAME);}

    //定义EditDemand队列
    @Bean
    public Queue topicD(){ return new Queue(QUEUE_EDIT_DEMAND_NAME);}
    //定义topic交换器
    @Bean
    TopicExchange topicExchange(){
        // 定义一个名为fanoutExchange的fanout交换器
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }
    //将定义的topicA队列与topicExchange交换机绑定
    @Bean
    public Binding bindingTopicExchangeWithA() {
        return BindingBuilder.bind(topicA()).to(topicExchange()).with(QUEUE_GOODS_BINDING_KEY);
    }
    //将定义的topicB队列与topicExchange交换机绑定
    @Bean
    public Binding bindingTopicExchangeWithB(){
        return BindingBuilder.bind(topicB()).to(topicExchange()).with(QUEUE_DEMAND_BINDING_KEY);
    }
    @Bean
    public Binding bindingTopicExchangeWithC(){
        return BindingBuilder.bind(topicC()).to(topicExchange()).with(QUEUE_EDIT_GOODS_BINDING_KEY);
    }
    @Bean
    public Binding bindingTopicExchangeWithD(){
        return BindingBuilder.bind(topicD()).to(topicExchange()).with(QUEUE_EDIT_DEMAND_BINDING_KEY);
    }
}
