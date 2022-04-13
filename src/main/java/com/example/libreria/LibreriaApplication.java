package com.example.libreria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class LibreriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}
        
        
        @Configuration
    public class SpringBootSecurityConfiguration extends WebSecurityConfigurerAdapter {

//        @Autowired
//	AuthenticationSuccessHandler successHandler;
            
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user").password("{noop}password").roles("USER").and()
                    .withUser("admin").password("{noop}password").roles("ADMIN");
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin/dashboard").hasAnyRole("ADMIN")
			.antMatchers("/user/dashboard").hasAnyRole("USER")
			.and().formLogin().loginPage("/login")
				//.successHandler(successHandler)
			.permitAll()
			.and().logout()
			.and().exceptionHandling().accessDeniedPage("/accessdenied");
            
        }
    }



}
