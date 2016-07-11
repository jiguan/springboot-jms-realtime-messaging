package com.jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.jms.dto.MessageDto;

@Service
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    
    /**
     * Simplify the send by using convertAndSend
     * 
     * @param text
     */
    public void sendText(final String text) {
        this.jmsTemplate.convertAndSend(text);
    }

    /**
     * Send text message to a specified destination
     * 
     * @param text
     */
    public void send(final MessageDto messageDto) {
        LOGGER.debug("Send message {}", messageDto);
        this.jmsTemplate.send(messageDto.to, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = session.createTextMessage(messageDto.content);
                return message;
            }
        });
    }
    
}
