package com.arpangautam27.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arpangautam27.journalApp.cache.AppCache;
import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AppCache appCache;

	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers() {
		List<User> allUsers= userService.getAll();
		if(allUsers != null && !allUsers.isEmpty()) {
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/create-admin-user")
	public User createAdminUser(@RequestBody User user) {
		userService.saveAdmin(user);
		return user;
	}
	
	@GetMapping("/clear-app-cache")
	public void clearAppCache() {
		appCache.init();
	}
}
