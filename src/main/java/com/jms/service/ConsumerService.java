package com.jms.service;

import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private static Logger LOGGER =  LoggerFactory.getLogger(ConsumerService.class);
}
