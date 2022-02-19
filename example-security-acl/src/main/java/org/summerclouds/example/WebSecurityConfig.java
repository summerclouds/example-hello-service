package org.summerclouds.example;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.summerclouds.common.security.jwt.DaoJwtAuthenticationProvider;
import org.summerclouds.common.security.jwt.JwtConfigurer;
import org.summerclouds.common.security.permissions.PermSet;
import org.summerclouds.common.security.permissions.ResourceAceVoter;
import org.summerclouds.common.security.permissions.RoleAceVoter;

// https://www.baeldung.com/spring-security-expressions

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	

        auth.inMemoryAuthentication()
        .withUser("user").password(encoder().encode("user"))
        .authorities("USER")
        .and()
        .withUser("admin").password(encoder().encode("admin"))
        .authorities(
        		new PermSet("web:*:/secret:Access secrets", "role:*:admin:Access admin role")
//        		,new SimpleGrantedAuthority("ADMIN")
        		)
        ;
        
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
        .antMatchers("/secret").hasAuthority("ace_web:${method}:${url}")
        .accessDecisionManager(accessDecisionManager())
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
	public AccessDecisionManager accessDecisionManager() {
		
	    List<AccessDecisionVoter<? extends Object>> decisionVoters 
	      = Arrays.asList(
		    new CustomBasedVoter(),
  		    new ResourceAceVoter(),
	        new WebExpressionVoter(),
	        new RoleAceVoter(), // instead of RoleVoter()
	        new AuthenticatedVoter()
	        );
	    return new AffirmativeBased(decisionVoters);
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