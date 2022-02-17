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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.summerclouds.common.security.jwt.DaoJwtAuthenticationProvider;
import org.summerclouds.common.security.jwt.JwtConfigurer;
import org.summerclouds.common.security.permissions.ResourceAceVoter;
import org.summerclouds.common.security.permissions.RoleAceVoter;
import org.summerclouds.common.security.realm.MemoryRoleAclRealm;
import org.summerclouds.common.security.realm.MemoryRoleRealm;
import org.summerclouds.common.security.realm.MemoryUserAclRealm;
import org.summerclouds.common.security.realm.MemoryUserRealm;
import org.summerclouds.common.security.realm.MemoryUserRolesRealm;
import org.summerclouds.common.security.realm.Realm;
import org.summerclouds.common.security.realm.RealmManager;

// https://www.baeldung.com/spring-security-expressions

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.userDetailsService(realmManager());
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
        .authorizeRequests()
        .antMatchers("/secret").hasAuthority("ace_web:${method}:${url}")
        .accessDecisionManager(accessDecisionManager())
        .and()
        .apply(new JwtConfigurer<>())
        .and()
          .httpBasic()
          ;    

    }

	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**")
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedOrigins("*")
		.allowedHeaders("*");
	}

	@Bean
	RealmManager realmManager() {
		return new RealmManager();
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		
	    List<AccessDecisionVoter<? extends Object>> decisionVoters 
	      = Arrays.asList(
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

    @Bean
    public Realm userRealm() {
    	return new MemoryUserRealm().add("user", "user").add("admin", "admin");
    }

    @Bean
    public Realm userRolesRealm() {
    	return new MemoryUserRolesRealm().add("user", "USER").add("admin", "ADMIN");
    }
    
    @Bean
    public Realm userAclRealm() {
    	return new MemoryUserAclRealm().add("admin", "*");
    }
    
    @Bean
    public Realm roleAclRealm() {
    	return new MemoryRoleAclRealm().add("user", "web:*:/secret");
    }
    
    @Bean
    public Realm roleRealm() {
    	return new MemoryRoleRealm().add("admin").add("user");
    }
    
}