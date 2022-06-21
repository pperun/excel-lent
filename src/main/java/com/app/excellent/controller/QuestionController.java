package com.app.excellent.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QuestionController {
    @MessageMapping("/questions")
    public Map<String, String> processQuestion(String question, Principal principal) {
        Map<String, String> content = new HashMap<>();
        content.put("id", IdGenerator.generateId(32));
        content.put("question", question);
        content.put("user", principal.getName());
        return content;
    }
}
