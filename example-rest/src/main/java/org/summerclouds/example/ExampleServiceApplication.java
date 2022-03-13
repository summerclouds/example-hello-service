package org.summerclouds.example;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.summerclouds.common.security.realm.Realm;
import org.summerclouds.common.security.realm.SimpleDummyRealm;

@SpringBootApplication(scanBasePackages = { "org.summerclouds"})
public class ExampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}

	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@PostConstruct
	public void postMapping() throws Exception {
//        RequestMappingInfo mappingInfo = RequestMappingInfo.paths("/jwt_token").methods(RequestMethod.POST).build();
//        Method method = JwtTokenController.class.getMethod("createToken", long.class);
//        requestMappingHandlerMapping.registerMapping(mappingInfo, new JwtTokenController(), method);
				    
	}
	
	@Bean
	public Realm dummyRealm() {
		return new SimpleDummyRealm();
	}
	
}
