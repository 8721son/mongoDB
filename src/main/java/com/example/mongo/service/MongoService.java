package com.example.mongo.service;

import com.example.mongo.entity.User;
import com.example.mongo.repository.UserRepository;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoService {

    private final UserRepository userRepository;
    static MongoTemplate mongo = new MongoTemplate(MongoClients.create(), "database");

    private final MongoTransactionManager mongoTransactionManager;

    public void save(){

        mongo.insert(new User("son",29));
//        mongo.dropCollection("user");

        userRepository.save(new User("kyeongmin",16));
    }

    public User getUser(){
        Optional<User> user = userRepository.findByUsername("son");
        return user.get();
    }

    public User getUserByMongoTemplates(){
        User user = mongo.findOne(Query.query(Criteria.where("username").is("son")), User.class);
        return user;
    }

    public void transactionInsert(){
        mongo.setSessionSynchronization(SessionSynchronization.ALWAYS);
        TransactionTemplate transactionTemplate = new TransactionTemplate(mongoTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                mongo.insert(new User("Kim", 20));
                mongo.insert(new User("Jack", 45));
            }
        });
    }

    public void shard(){
        MongoDatabase db = mongo.getMongoDatabaseFactory().getMongoDatabase("admin");
        db.runCommand(new Document("enableSharding", "database"));

        Document shardCmd = new Document("shardCollection", "database.user")
                .append("key", new Document("age", 30).append("username", "son"));

        db.runCommand(shardCmd);

    }
}
