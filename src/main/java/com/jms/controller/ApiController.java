package com.jms.controller;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jms.dto.MessageDto;
import com.jms.service.MessageService;

@RestController
public class ApiController {
    
    private static Logger LOGGER =  LoggerFactory.getLogger(ApiController.class);
    
    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
    
    @Autowired
    MessageService service;
    
    
    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json")
    public void sendMessage(@RequestBody MessageDto message, Principal currentUser) {
        message.from = currentUser.getName();
        // send any message sent by clients to a queue called rt_messages
        service.send(message);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public Set<String> getUsers() {
        // get the list of users from Spring Security's session registry
        LOGGER.debug("Logged in user num: {}", sessionRegistry.getAllPrincipals().size());
        return sessionRegistry.getAllPrincipals().stream().map(u -> ((User) u).getUsername()).collect(Collectors.toSet());
    }

}
