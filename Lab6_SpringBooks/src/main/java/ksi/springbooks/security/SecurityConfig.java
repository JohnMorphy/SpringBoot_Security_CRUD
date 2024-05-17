package ksi.springbooks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http
		.csrf(Customizer.withDefaults())
		.authorizeHttpRequests(authorize -> authorize
		.requestMatchers("/new_book", "/edit_book/**", "/delete_book/**").hasRole("BOOK_MANAGER")
		.requestMatchers("/new_book", "/edit_book/**", "/delete_book/**",
				"/new_category", "/edit_category/**", "/delete_category/**",
				"new_publisher", "/edit_publisher/**", "/delete_publisher/**").hasRole("ADMIN")
		.anyRequest().permitAll()
		)
	.formLogin(form -> form
			 .loginPage("/login")
			 .loginProcessingUrl("/login")
			 .defaultSuccessUrl("/")
			 .permitAll())
	.logout(logout -> logout
			.logoutUrl("/logout")
		    .logoutSuccessUrl("/logout_message")
		);
	
	return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
	}
	
	
}
