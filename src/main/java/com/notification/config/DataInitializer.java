package com.notification.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.notification.entity.User;
import com.notification.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Qualifier("dataInit")
@Slf4j
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		log.info("run ...");
		if (userRepository.count() == 0) {
			User user = User.builder().username("toantt").password(this.passwordEncoder.encode("123456"))
					.roles(Arrays.asList("ROLE_USER")).build();
			user.setCreatedBy("toantt");
			user.setCreatedDate(Instant.now());
			user.setDeleted(false);
			user.setVersion(1L);

			User admin = User.builder().username("admin").password(this.passwordEncoder.encode("123456"))
					.roles(Arrays.asList("ROLE_ADMIN")).build();
			admin.setCreatedBy("toantt");
			admin.setCreatedDate(Instant.now());
			admin.setDeleted(false);
			admin.setVersion(1L);

			this.userRepository.saveAndFlush(user);
			this.userRepository.saveAndFlush(admin);
		}
	}
}
