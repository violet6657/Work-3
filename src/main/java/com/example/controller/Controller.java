package com.example.controller;

import com.example.Constans;
import com.example.service.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {
    @Autowired
    private Tools tool;
    @RequestMapping("/start")
    public String start()  {
        return String.join(System.lineSeparator(), Constans.answer);
    }
}

