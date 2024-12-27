package com.ecommerce.gadgetzone.config;

import com.ecommerce.gadgetzone.entity.User;
import com.ecommerce.gadgetzone.enums.Role;
import com.ecommerce.gadgetzone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitialDataPersistence implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
       //saveAdmin();
    }

    private void saveAdmin(){
        User admin = User.builder()
                .email("bleonasejdini24@gmail.com")
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("Bleona12345@"))
                .firstName("Bleona")
                .lastName("Sejdini")
                .build();
        userRepository.save(admin);
    }
}