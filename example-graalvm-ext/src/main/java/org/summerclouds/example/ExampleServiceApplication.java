package org.summerclouds.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.TypeHint;
import org.summerclouds.common.core.internal.SpringSummerCloudsCoreAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "org.summerclouds"})
@NativeHint(types = @TypeHint(types = SpringSummerCloudsCoreAutoConfiguration.class))
public class ExampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}
	
}
