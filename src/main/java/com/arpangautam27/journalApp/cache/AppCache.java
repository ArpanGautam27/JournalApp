package com.arpangautam27.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arpangautam27.journalApp.entity.ConfigJournalAppEntity;
import com.arpangautam27.journalApp.repository.ConfigJournalAppRepository;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class AppCache {

	private Map<String, String> appCache;
	
	@Autowired
	private ConfigJournalAppRepository configJournalAppRepository;
	
	@PostConstruct
	public void init() {
		appCache = new HashMap<>();
		List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
		for (ConfigJournalAppEntity configJournalAppEntity : all) {
			appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
		}
	}
}
