package com.example.Login.configuration;

import com.example.Login.entity.User;
import com.example.Login.enums.Roles;
import com.example.Login.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

//    @Bean
//    ApplicationRunner applicationRunner(UserRepository userRepository){
//        return args -> {
//            if (userRepository.findByUsername("admin4").isEmpty()){
//                var roles = new HashSet<String>();
//                roles.add(Roles.ADMIN.name());
//
//                User user = User.builder()
//                        .username("admin4")
//                        .password(passwordEncoder.encode("admin123"))
//                         .roles(roles)
//                        .build();
//
//                userRepository.save(user);
//                log.warn("admin user has been created with default password: admin, please change it");
//            }
//        };
//    }
}