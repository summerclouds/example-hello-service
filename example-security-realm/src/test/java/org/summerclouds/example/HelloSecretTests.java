
package org.summerclouds.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.summerclouds.common.core.tool.MHttp;
import org.summerclouds.common.core.tool.MJson;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloSecretTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noAuth() throws Exception {

		this.mockMvc.perform(get("/secret")).andDo(print()).andExpect(status().isUnauthorized());
	}
	
	@Test
	public void withBasicAuth() throws Exception {

		this.mockMvc.perform(get("/secret").headers(getBasicAuth("admin", "admin")) ).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello, World!"));;
	}
	
	@Test
	public void withJwtAuth() throws Exception {
		// 1. create jwt token
		String token = null;
		{
			
			MvcResult result = this.mockMvc.perform(post("/jwt_token").headers(getBasicAuth("user", "user")) ).andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$.username").value("user"))
					.andReturn();
			
			String resultContent = result.getResponse().getContentAsString();
			JsonNode j = MJson.load(resultContent);
			token = j.get("token").asText();
			System.out.println("Token: " + token);
		}
		assertNotNull(token);
		
		// 2. access secret
		
		this.mockMvc.perform(get("/secret").headers(getJwtAuth(token))).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.content").value("Hello, World!"));;

		
	}
	

	public HttpHeaders getJwtAuth(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		return headers;
	}
	
	public HttpHeaders getBasicAuth(String user, String passwd) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(MHttp.HEADER_AUTHORIZATION, MHttp.toBasicAuthorization(user, passwd));
		return headers;
	}
}
