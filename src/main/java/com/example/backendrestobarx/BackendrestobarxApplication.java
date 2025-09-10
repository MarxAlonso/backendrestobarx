package com.example.backendrestobarx;

import com.example.backendrestobarx.entity.User;
import com.example.backendrestobarx.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
@RequiredArgsConstructor
public class BackendrestobarxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendrestobarxApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Crear usuario admin si no existe
			if (!userRepository.existsByEmail("admin@restobarx.com")) {
				User admin = new User();
				admin.setName("Administrador");
				admin.setEmail("admin@restobarx.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole(User.Role.ADMIN);
				admin.setPhone("+51 999 000 001");
				userRepository.save(admin);
			}

			// Crear usuario cliente si no existe
			if (!userRepository.existsByEmail("cliente@restobarx.com")) {
				User client = new User();
				client.setName("Cliente Prueba");
				client.setEmail("cliente@restobarx.com");
				client.setPassword(passwordEncoder.encode("cliente123"));
				client.setRole(User.Role.CLIENT);
				client.setPhone("+51 999 000 002");
				userRepository.save(client);
			}
		};
	}
}
