# SpringBoot_Security_CRUD
Spring Boot app built with Eclipse with CRUD, and spring security built with roles that runs on locally hosted postgres.

// app view
![image](https://github.com/JohnMorphy/SpringBoot_Security_CRUD/assets/92916894/d707020a-ba53-4652-bb07-4a6a88efbdb7)


## Code snippets

### Security config (ommited imports)

In this app sercurity operates on roles.

```java
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
```

### User model (ommited getters and setters and imports)

```java
@Entity
@Table(name = "users")

public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String username;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Roles> roles = new ArrayList<>();
 ...
}
```

### User role model (connected to UserEntity through pivot table)

```java
@Entity
@Table(name = "roles")

public class Roles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
 ...
}
```
