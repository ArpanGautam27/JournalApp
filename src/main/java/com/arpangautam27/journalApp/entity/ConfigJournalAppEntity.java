package com.arpangautam27.journalApp.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

	private String key;
	
	private String value;
}
