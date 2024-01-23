package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class CustomerController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/index")
    public String index(){
        return "hello world";
    }

    @GetMapping("/index2")
    public String index2(){
        return "hello world whithout security";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession(){
        String sessionId = "";
        User user = null;
        List<Object> sessions = this.sessionRegistry.getAllPrincipals();
        for(Object session:sessions){
            if(session instanceof User){
                user = (User) session;
            }
            List<SessionInformation> sessionInformations = this.sessionRegistry.getAllSessions(session,false);
            for(SessionInformation sessionInformation: sessionInformations){
                sessionId = sessionInformation.getSessionId();

            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("response", "Hello world");
        response.put("sessionId", sessionId);
        response.put("sessionUser", user);
        return (ResponseEntity<?>) ResponseEntity.ok(response);
    }
}
