package com.arpangautam27.journalApp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.arpangautam27.journalApp.enums.Sentiment;

import lombok.Data;

@Data

@Document(collection = "journal_entries")
public class JournalEntry {
	@Id
	private ObjectId id;
	
	private String title;
	
	private String content;
	
	private LocalDateTime date;

	private Sentiment sentiment;
}
