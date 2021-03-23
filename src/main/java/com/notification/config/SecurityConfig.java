package com.notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.notification.jwt.JwtConfigurer;
import com.notification.jwt.JwtTokenProvider;
import com.notification.repository.UserRepository;

@Configuration
@ComponentScan(value = { "com.spring.security.*" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
	@Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
	
	@PostConstruct
    public void initialize() {
		logger.info("---------------> Firebase application has been initialized");
		System.out.println("----> firebaseConfigPath " + firebaseConfigPath);
		try {
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
					.build();
			System.out.println("----> options " + options);
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				logger.info("---------------> Firebase application has been initialized");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
    }
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//	    return super.userDetailsService();
//	}
	
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		// TODO Auto-generated method stub
//		return super.authenticationManagerBean();
//	}
	
	@Bean("userDetailsService")
	public UserDetailsService customUserDetailsService(UserRepository repo) {
		return (userName) -> repo.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Username: " + userName + " not found"));
	}
	
	@Bean
	public AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder encoder) {
		return authentication -> {
			String username = authentication.getPrincipal() + "";
			String password = authentication.getCredentials() + "";

			UserDetails user = userDetailsService.loadUserByUsername(username);
			if (!encoder.matches(password, user.getPassword())) {
				throw new BadCredentialsException("Bad credentials");
			}

			if (!user.isEnabled()) {
				throw new DisabledException("User account is not active");
			}
			return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
		};
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
//        .httpBasic().disable()
//        .csrf().disable()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
		
		.httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(c-> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        
            .authorizeRequests()
            .antMatchers("/auth/signin").permitAll()
            .antMatchers(HttpMethod.GET, "/v1/employees/**")
//            	.authenticated()
            	//hasRole("USER")
            .permitAll()
            .antMatchers(HttpMethod.DELETE, "/v1/employees/**")
//            .hasRole("ADMIN")
//            .antMatchers(HttpMethod.GET, "/v1/vehicles/**")
            .permitAll()
            
            .antMatchers(HttpMethod.GET, "/v1/departments/**")
            .permitAll()
            .antMatchers(HttpMethod.DELETE, "/v1/departments/**")
            .permitAll()
            
            .anyRequest().authenticated()
        .and()
        .apply(new JwtConfigurer(jwtTokenProvider));
		//@formatter:on
	}
}
