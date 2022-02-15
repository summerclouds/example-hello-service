package org.summerclouds.example;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/hello")
	@Secured("ace_rest")
	public Hello greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Hello(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping("/secret")
	public Hello secret(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Hello(counter.incrementAndGet(), String.format(template, name));
	}
	
}
