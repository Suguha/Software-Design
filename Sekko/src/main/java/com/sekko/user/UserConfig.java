package com.sekko.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class UserConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;

	@Autowired
	public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(User.getPasswordEncoder());		
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("sekko Authentication");
		http.exceptionHandling().authenticationEntryPoint(entryPoint);
		http.authorizeRequests()
		.antMatchers("/manage/**").hasAnyAuthority("admin")
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.csrf().disable();
	}
}
