package com.arpangautam27.journalApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arpangautam27.journalApp.filter.JwtFilter;
import com.arpangautam27.journalApp.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	    @Autowired
	    private UserDetailsServiceImpl userDetailsService;
	    
	    @Autowired
	    private JwtFilter jwtFilter;

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


	        return http.authorizeHttpRequests(request -> request
	                        .requestMatchers("/public/**").permitAll()
	                        .requestMatchers("/journal/**", "/user/**").authenticated()
	                        .requestMatchers("/admin/**").hasRole("ADMIN")
	                        .anyRequest().authenticated())
	                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	                .csrf(AbstractHttpConfigurer::disable)
	                .build();
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }
	    
	    
	   
	    @Bean
	    AuthenticationManager authenticationManager() {
	        
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        
	        return new ProviderManager(authProvider);
	    }

	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}