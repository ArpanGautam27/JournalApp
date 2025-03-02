package com.arpangautam27.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.arpangautam27.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{

	User findByUserName(String username);
	
	void deleteByUserName(String username);
}
