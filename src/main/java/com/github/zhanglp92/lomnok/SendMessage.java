package com.github.zhanglp92.lomnok;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.Map;

@Log4j2
public class SendMessage {

    @SneakyThrows(value = Exception.class)
    public void send() {
        Message msg = Message.builder().id(1L).msg("test").sendTime(new Date()).build();
        log.info("msg={}", msg.toString());
    }

    public void batchSend() {
        Map<Long, Message> messages = Maps.newHashMap();
        for (long i = 0; i < 10; i++) {
            messages.put(i, Message.builder().id(i).msg("test").sendTime(new Date()).build());
        }
        log.info("messages={}", JSON.toJSONString(messages));
    }

    public static void main(String[] args) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.send();
        sendMessage.batchSend();
    }
}
