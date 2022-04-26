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
		new Benchmark(new MArgs(args,
				MArgs.opt('c', null, 1, false, "Amount of objects to create"),
				MArgs.opt('l', null, 1, false, "Amount of read loops")
				), repository).run();
	}
	
}
