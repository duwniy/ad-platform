package com.adplatform;

import com.adplatform.user.entity.User;
import com.adplatform.user.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class AdPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdPlatformApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializer(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            List<User> users = repo.findAll();
            for (User u : users) {
                if ("$2a$10$hash_here".equals(u.getPassword())) {
                    u.setPassword(encoder.encode("password123"));
                    repo.save(u);
                }
            }
        };
    }
}
