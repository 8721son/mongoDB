package com.example.mongo.controller;

import com.example.mongo.service.MongoService;
import com.example.mongo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MongoController {


    private final MongoService mongoService;

    @GetMapping("/")
    public String save(){
        mongoService.save();
        return null;
    }

    @GetMapping("/getUser")
    public User getUser(){
        return mongoService.getUser();
    }

    @GetMapping("/getUser2")
    public User getUserByMongoTemplate(){
        return mongoService.getUserByMongoTemplates();
    }

    @GetMapping("/shard")
    public void shard(){
        mongoService.transactionInsert();
    }
}
