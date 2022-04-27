package org.summerclouds.example;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	PageEntryRepository repo;
	
	@GetMapping("/hello")
	public Hello hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		
		PageEntry entry = new PageEntry();
		
		repo.save(entry);
		
		long cnt = repo.count();
		
		name = name  + " " + cnt;
		
		return new Hello(counter.incrementAndGet(), String.format(template, name));
	}

}
