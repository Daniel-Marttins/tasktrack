package com.codelab.tasktrack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/ping")
public class AppController {

    @GetMapping
    public ResponseEntity<?> PingApp() {
        return ResponseEntity.status(HttpStatus.OK).body("API working correctly!");
    }

}
