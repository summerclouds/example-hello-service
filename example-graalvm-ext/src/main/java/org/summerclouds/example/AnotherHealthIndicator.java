package org.summerclouds.example;

import java.util.Date;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AnotherHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		Date date = new Date();
		return Health.up().withDetail("date", date).build();
	}

}
