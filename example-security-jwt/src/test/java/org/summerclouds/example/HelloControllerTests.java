
package org.summerclouds.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noParamHelloShouldReturnDefaultMessage() throws Exception {

		this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello, World!"));
	}

	@Test
	public void paramHelloShouldReturnTailoredMessage() throws Exception {

		this.mockMvc.perform(get("/hello").param("name", "John Doe"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello, John Doe!"));
	}
	
}
