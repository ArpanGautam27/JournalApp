package com.arpangautam27.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
//	@Disabled //disable the test for current testrun 
	@ParameterizedTest
	@CsvSource({
		"Ram",
		"Shyam",
		"Arpan"
	})
	public void testFindByUserName() {
		assertNotNull(userRepository.findByUserName("Ram"));
	}
	
	@ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }
	
	@Disabled
	@ParameterizedTest
	@CsvSource({
		"5,2,7", // 5 = a , 2 = b, 7 = expected
		"2,2,4",
		"3,3,9"
	})
	public void test(int a, int b, int expected) {
		assertEquals(expected, a+b);
	}
}




//@BeforeAll @BeforeEach , @AfterAll, @AfterEach  these annotations are used if we want to run 
// some code before all methods or before each method