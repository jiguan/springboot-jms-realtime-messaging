package com.jms.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.jms.dto.MessageDto;

/**
 * Receives messages from ActiveMQ and relays them to appropriate users.
 */
@Component(value = "queueHandler")
public class QueueHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueHandler.class);


    @Autowired
    private SimpMessageSendingOperations msgTemplate;

    private static Map<String, Object> defaultHeaders;

    static {
        defaultHeaders = new HashMap<String, Object>();
        // add the Content-Type: application/json header by default
        defaultHeaders.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
    }

    public void handle(Exchange exchange) {
        LOGGER.info("handle exchange");
        Message camelMessage = exchange.getIn();
        MessageDto message = camelMessage.getBody(MessageDto.class);
        // send the message specifically to the destination user by using STOMP's user-directed
        // messaging
        msgTemplate.convertAndSendToUser(message.to, "/topic/messages", message, defaultHeaders);
    }
}
