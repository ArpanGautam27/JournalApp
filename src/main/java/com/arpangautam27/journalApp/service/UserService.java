package com.arpangautam27.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
//	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
//	use @slf4j	instead of Logger logger;
	
	public void saveUser(User user) {
			userRepository.save(user); 
	}
	
	public boolean saveNewUser(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			log.error("Error Occured for {} ",user.getUserName() ,e); 
//			log.error is bydefault provided by slf4j
//			logger.info(null);      // without @slf4j annotation with Logger logger;
//			logger.warn(null);
//			logger.debug(null);
//			logger.trace(null);
			return false;
		}
		

	}
	
	public void saveAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER" , "ADMIN"));
		userRepository.save(user);
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	
	public Optional<User> findById(ObjectId myId) {
		return userRepository.findById(myId);
	}
	
	
	public void deleteById(ObjectId myId) {
		userRepository.deleteById(myId);
	}
	
	public void deleteByUserName(String username) {
		userRepository.deleteByUserName(username);
	}
	
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}
}
