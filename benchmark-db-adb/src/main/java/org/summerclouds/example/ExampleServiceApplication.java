package org.summerclouds.example;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.summerclouds.common.core.M;
import org.summerclouds.common.core.util.MArgs;
import org.summerclouds.common.db.xdb.XdbManager;

@SpringBootApplication(scanBasePackages = { "org.summerclouds"})
public class ExampleServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}

//	@Bean
//	XdbManager xdbManager() {
//		return new XdbManager();
//	}

	@Override
	public void run(String... args) throws Exception {
		XdbManager manager = M.l(XdbManager.class);
		System.out.println(Arrays.deepToString(manager.getServiceNames()));
		new Benchmark(new MArgs(new String[0]),  manager.getService("default")).run();
	}
	
}
