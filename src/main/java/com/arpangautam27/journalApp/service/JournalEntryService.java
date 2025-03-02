package com.arpangautam27.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arpangautam27.journalApp.entity.JournalEntry;
import com.arpangautam27.journalApp.entity.User;
import com.arpangautam27.journalApp.repository.JournalEntryRepository;

import lombok.extern.java.Log;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public void saveEntry(JournalEntry journalEntry, String userName) {
		User user = userService.findByUserName(userName);
		journalEntry.setDate(LocalDateTime.now());
		JournalEntry savedEntry= journalEntryRepository.save(journalEntry);
		user.getJournalEntries().add(savedEntry);
		userService.saveUser(user);
	}
	
	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry); 
	}
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> findById(ObjectId myId) {
		return journalEntryRepository.findById(myId);
	}
	
	@Transactional
	public boolean deleteById(ObjectId myId, String userName) {
		boolean removed = false;
		try {
			User user = userService.findByUserName(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
			if(removed) {
				userService.saveUser(user);
				journalEntryRepository.deleteById(myId);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occured while deleting the entry "+ e);
		}
		return removed;
	}

}
