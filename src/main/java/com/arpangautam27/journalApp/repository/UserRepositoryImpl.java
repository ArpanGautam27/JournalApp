package com.arpangautam27.journalApp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.arpangautam27.journalApp.entity.User;

public class UserRepositoryImpl {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<User> getUserForSA(){
		Query query = new Query();
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.orOperator(
				Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"),
				Criteria.where("sentimentAnalysis").is(true)));
		return mongoTemplate.find(query, User.class);
	}
}
