package com.itheima.mp.controller;

import com.itheima.mp.service.impl.BlogEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/blogEvaluate")
public class BlogEvaluateController {
    @Autowired
    BlogEvaluateService service;
}
