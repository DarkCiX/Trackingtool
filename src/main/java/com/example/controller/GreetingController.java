package com.example.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.model.HelloMessage;



@Controller
@CrossOrigin(origins="*",allowedHeaders="*")
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return HtmlUtils.htmlEscape(message.getName());
    }
    
    
    
}
