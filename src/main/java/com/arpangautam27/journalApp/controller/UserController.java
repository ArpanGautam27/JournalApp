package com.arpangautam27.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arpangautam27.journalApp.api.response.WeatherResponse;
import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.service.UserService;
import com.arpangautam27.journalApp.service.WeatherService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Read, Update & Delete User")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private WeatherService weatherService;
	
	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userInDb = userService.findByUserName(userName);
		if(userInDb != null) {
			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveNewUser(userInDb);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userService.deleteByUserName(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping
	public ResponseEntity<?> greetings(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
	        String greeting = "";
	        if (weatherResponse != null) {
	            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
	        }
	        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
	}
}
