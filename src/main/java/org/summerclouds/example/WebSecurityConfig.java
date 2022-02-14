package org.summerclouds.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.summerclouds.common.security.jwt.DaoJwtAuthenticationProvider;
import org.summerclouds.common.security.jwt.JwtConfigurer;

// https://www.baeldung.com/spring-security-expressions

@Configuration
//@EnableAutoConfiguration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	

        auth.inMemoryAuthentication()
        .withUser("user").password(encoder().encode("user"))
        .authorities("USER")
        .and()
        .withUser("admin").password(encoder().encode("admin"))
        .authorities("ADMIN");
        
        auth.authenticationProvider(new DaoJwtAuthenticationProvider(auth.getDefaultUserDetailsService()));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/hello");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.csrf().disable()
        .formLogin().disable()
        .logout().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
//    	.anonymous().principal("user").authenticationProvider(new AnonymousAuthenticationProvider(encoder().encode("user1Pass")))
//        .and()
        .authorizeRequests()
        .antMatchers("/secret").hasAuthority("ADMIN")
//        .antMatchers("/jwt_token").permitAll()
        .and()
        .apply(new JwtConfigurer<>())
        .and()
          .httpBasic()
          ;    
//    	http
//        .authorizeRequests()
//        
//        .antMatchers("/secret/*").hasAuthority("ADMIN")
//        .antMatchers("/*").permitAll();

//        http.authorizeRequests().anyRequest().authenticated()
//            .and().httpBasic();
        
//		http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    	http.addFilterBefore(new InterceptorFilter(), BasicAuthenticationFilter.class);

    }

	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**")
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedOrigins("*")
		.allowedHeaders("*");
	}

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//        .csrf().disable()
//        .exceptionHandling().authentication
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//        .authorizeRequests()
//        .anyRequest().authenticated();
//    	
//		httpSecurity.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//    	httpSecurity.addFilterBefore(new InterceptorFilter(), BasicAuthenticationFilter.class);
//    	httpSecurity.headers().cacheControl();
//    	
//    }
    
    
}