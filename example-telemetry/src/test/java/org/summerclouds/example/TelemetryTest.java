package org.summerclouds.example;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TelemetryTest {

	@Test
	public void test() {
		GenericContainer otel = new GenericContainer(DockerImageName.parse("otel/opentelemetry-collector"));
		otel.addExposedPort(13133);
		otel.addExposedPort(14250);
		otel.addExposedPort(14268);
		otel.addExposedPort(14250);
		otel.addExposedPort(55678);
		otel.addExposedPort(55679);
		otel.addExposedPort(4317);
		otel.addExposedPort(8888);
		otel.addExposedPort(9411);
		otel.withFileSystemBind(new File("etc/otel-config.yaml").getAbsolutePath(), "/otel-local-config.yaml");
		otel.setCommand("--config otel-local-config.yaml");
		
	}
}
