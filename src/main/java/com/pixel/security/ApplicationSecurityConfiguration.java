package com.pixel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	@Bean
	public DaoAuthenticationProvider authentificationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(getPasswordEncode());
		return provider;
		
	}
	
	@Bean
	public PasswordEncoder getPasswordEncode() {
		
		return new BCryptPasswordEncoder();
		//return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		
		
		auth.authenticationProvider(authentificationProvider());
		

	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			.authorizeRequests()
			.antMatchers("/h2-console/**","/categories/**","/cinemas/**","/films/**","/places/**","/projections/**","/salles/**","/seances/**","/tickets/**","/villes/**","/css/**","/js/**","/jquery/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
					
	}

	
}
