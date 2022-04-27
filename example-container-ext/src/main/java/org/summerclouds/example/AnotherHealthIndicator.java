package org.summerclouds.example;

import java.util.Date;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AnotherHealthIndicator implements HealthIndicator {

	@SuppressWarnings("deprecation")
	@Override
	public Health health() {
		Date date = new Date();
		switch (date.getMinutes() % 3) {
		case 0:
			return Health.up().withDetail("date", date).build();
		case 1:
			return Health.outOfService().withDetail("date", date).build();
		default:
			return Health.down().withDetail("date", date).build();
		}
	}

}
