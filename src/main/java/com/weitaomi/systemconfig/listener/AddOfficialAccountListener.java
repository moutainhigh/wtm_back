package com.weitaomi.systemconfig.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.weitaomi.application.model.dto.AddResponseTaskDto;
import com.weitaomi.application.service.interf.IOfficeAccountService;
import com.weitaomi.systemconfig.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Map;

/**
 * Created by supumall on 2016/8/10.
 */
public class AddOfficialAccountListener implements ChannelAwareMessageListener {
    private Logger logger= LoggerFactory.getLogger(AddOfficialAccountListener.class);
    @Autowired
    private IOfficeAccountService officeAccountService;
    @Autowired
    private Gson2JsonMessageConverter messageConverter;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        channel.basicQos(100);
        String data=(String)messageConverter.fromMessage(message);
        Map params=(Map)JSONObject.parse(data);
        final List<Long> idList=(List<Long>)params.get("idList");
        final String unionId=(String)params.get("unionId");
        logger.info("获取数据成功：{}", JSON.toJSONString(params));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        if (idList!=null&& !StringUtil.isEmpty(unionId)){
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    officeAccountService.sendRequestToWechatDialog(unionId,idList);
                }
            });
        }
    }
}
