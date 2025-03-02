package com.arpangautam27.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.repository.UserRepository;


public class UserDetailsServiceImplTests {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;
	
	@Mock   // - If we use without SpringBootContext                              
//	@MockBean - if we use SpringBootContext
	private UserRepository userRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void loadUserByUsernameTest() {
		when(userRepository.findByUserName(ArgumentMatchers.anyString()))
		.thenReturn(User.builder().userName("ram").password("hfffjh")
												  .roles(new ArrayList<>()).build());
		UserDetails user =userDetailsService.loadUserByUsername("Ram");
		assertNotNull(user);
	}
}
