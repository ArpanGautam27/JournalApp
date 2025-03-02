package com.arpangautam27.journalApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.arpangautam27.journalApp.entity.JournalEntry;
import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.service.JournalEntryService;
import com.arpangautam27.journalApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllEntries")
	@Operation(summary = "Get all journal entries of a user")
	public ResponseEntity<?> getAllJournalEntriesOfUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> allEntries = user.getJournalEntries();
		if(allEntries != null && !allEntries.isEmpty()) {
			return new ResponseEntity<>(allEntries, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getById/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> collect=user.getJournalEntries().stream()
										.filter(x -> x.getId().equals(myId))
										.collect(Collectors.toList());
		if(!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
			if(journalEntry.isPresent()) {
				return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);	
			} 
		}
		return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/saveEntry")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalEntryService.saveEntry(journalEntry, userName);
			return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@DeleteMapping("/deleteById/{myId}")
	public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		boolean removed = journalEntryService.deleteById(myId, userName);
		if(removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/updateById/{myId}")
	public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId, 
			@RequestBody JournalEntry newJournalEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> collect=user.getJournalEntries().stream()
										.filter(x -> x.getId().equals(myId))
										.collect(Collectors.toList());
		if(!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
			if(journalEntry.isPresent()) {
				JournalEntry old = journalEntry.get();
				old.setTitle(newJournalEntry.getTitle() != null && !newJournalEntry.getTitle()
						.equals("") ? newJournalEntry.getTitle() : old.getTitle());
				old.setContent(newJournalEntry.getContent() != null && !newJournalEntry.getContent()
						.equals("") ? newJournalEntry.getContent() : old.getContent());
				journalEntryService.saveEntry(old);
				return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
