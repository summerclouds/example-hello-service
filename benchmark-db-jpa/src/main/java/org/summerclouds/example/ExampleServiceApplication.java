package org.summerclouds.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.summerclouds.common.core.M;
import org.summerclouds.common.core.util.MArgs;

@SpringBootApplication(scanBasePackages = { "org.summerclouds"})
public class ExampleServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PageEntryRepository repository = M.l(PageEntryRepository.class);
		new Benchmark(new MArgs(args), repository).run();
	}
	
}
