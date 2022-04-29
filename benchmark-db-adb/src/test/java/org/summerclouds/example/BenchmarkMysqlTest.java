package org.summerclouds.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.summerclouds.common.core.internal.SpringSummerCloudsCoreAutoConfiguration;
import org.summerclouds.common.core.tool.MFile;
import org.summerclouds.common.db.internal.SpringSummerCloudsDbAutoConfiguration;
import org.testcontainers.containers.MariaDBContainer;

@SpringBootTest(classes = {
		SpringSummerCloudsCoreAutoConfiguration.class,
		SpringSummerCloudsDbAutoConfiguration.class,
		TestApplication.class
})
@ActiveProfiles("test")
@Tag("mariadb")
public class BenchmarkMysqlTest {

	@Test
	public void benchmark() throws SQLException, ClassNotFoundException {
		try (MariaDBContainer mariaDB = new MariaDBContainer("mariadb:10.7.3")) {
			mariaDB.start();
			Class.forName("org.mariadb.jdbc.Driver");
			String url = mariaDB.getJdbcUrl();
			Connection con = DriverManager.getConnection(url, mariaDB.getUsername(), mariaDB.getPassword() );
			con.createStatement().execute(MFile.readFile(new File("sql/create_db.sql")));
			con.commit();
		}
	}

}
