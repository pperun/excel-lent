package com.app.excellent.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnswerController {
    @MessageMapping("/answers")
    public Map<String, String> processAnswer(String answer, Principal principal) {
        Map<String, String> content = new HashMap<>();
        JsonParser parser = JsonParserFactory.getJsonParser();
        List<Object> answerValues = parser.parseList(answer);
        content.put("id", (String)answerValues.get(1));
        content.put("answer", (String)answerValues.get(0));
        content.put("user", principal.getName());
        return content;
    }


}
