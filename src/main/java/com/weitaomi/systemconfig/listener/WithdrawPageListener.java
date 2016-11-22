package com.weitaomi.systemconfig.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.weitaomi.application.service.interf.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/12.
 */
public class WithdrawPageListener implements ChannelAwareMessageListener {
    private Logger logger= LoggerFactory.getLogger(WithdrawPageListener.class);
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private Gson2JsonMessageConverter messageConverter;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        channel.basicQos(100);
        String params=(String)messageConverter.fromMessage(message);
        final Map<String,Object> param= (Map<String,Object>)JSONObject.parse(params);
        logger.info("审核ID为{}的提现申请进入审核队列！",param.get("approveId"));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (paymentService.dealWithdrawsChange(param)){
                    logger.info("审核ID为{}的提现申请审核成功！",param.get("approveId"));
                }
            }
        });
        if (paymentService.dealWithdrawsChange(param)){
            logger.info("审核ID为{}的提现申请审核成功！",param.get("approveId"));
        }
    }
}
