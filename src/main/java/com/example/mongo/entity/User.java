package com.example.mongo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Data
@Sharded(shardKey = {"username","age"})
public class User {

    @Id
    private ObjectId id;
    
    private String username;
    private int age;

    public User(String username, int age) {
        this.username=username;
        this.age=age;
    }

}
