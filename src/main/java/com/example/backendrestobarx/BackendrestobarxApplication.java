package com.example.backendrestobarx;

import com.example.backendrestobarx.entity.Category;
import com.example.backendrestobarx.entity.MenuItem;
import com.example.backendrestobarx.entity.User;
import com.example.backendrestobarx.repository.CategoryRepository;
import com.example.backendrestobarx.repository.MenuItemRepository;
import com.example.backendrestobarx.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendrestobarxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendrestobarxApplication.class, args);
	}
	@Bean
	CommandLineRunner init(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			CategoryRepository categoryRepository,
			MenuItemRepository menuItemRepository) {

		return args -> {

			// ===== Usuarios =====
			if (!userRepository.existsByEmail("admin@restobarx.com")) {
				User admin = new User();
				admin.setName("Administrador");
				admin.setEmail("admin@restobarx.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole(User.Role.ADMIN);
				admin.setPhone("+51 999 000 001");
				userRepository.save(admin);
			}

			if (!userRepository.existsByEmail("cliente@restobarx.com")) {
				User client = new User();
				client.setName("Cliente Prueba");
				client.setEmail("cliente@restobarx.com");
				client.setPassword(passwordEncoder.encode("cliente123"));
				client.setRole(User.Role.CLIENT);
				client.setPhone("+51 999 000 002");
				userRepository.save(client);
			}

			// ===== Categorías =====
			Category parrillas = categoryRepository
					.findByName("Parrillas")
					.orElseGet(() -> categoryRepository.save(
							new Category(null, "Parrillas",
									"Carnes a la parrilla", true)));

			Category bebidas = categoryRepository
					.findByName("Bebidas")
					.orElseGet(() -> categoryRepository.save(
							new Category(null, "Bebidas",
									"Refrescos y tragos", true)));

			Category postres = categoryRepository
					.findByName("Postres")
					.orElseGet(() -> categoryRepository.save(
							new Category(null, "Postres",
									"Dulces y postres caseros", true)));

			// ===== Menu Items =====
			if (menuItemRepository.count() == 0) {
				menuItemRepository.saveAll(List.of(
						new MenuItem(null, "Parrilla Mixta",
								"Carne de res, pollo y chorizo a la brasa",
								new BigDecimal("45.00"), parrillas,
								"https://okrecetas.com/recetas-de-carnes/img600/parrillada-mixta.jpg",
								true, LocalDateTime.now(), LocalDateTime.now()),

						new MenuItem(null, "Limonada ",
								"Limonada fresca con hierbabuena",
								new BigDecimal("8.00"), bebidas,
								"https://brasaycarbon.pe/wp-content/uploads/2020/12/bebida-limonada-medio-litro.jpg",
								true, LocalDateTime.now(), LocalDateTime.now()),

						new MenuItem(null, "Cheesecake",
								"Cheesecake de frutos rojos",
								new BigDecimal("12.00"), postres,
								"https://www.splenda.com/wp-content/themes/bistrotheme/assets/recipe-images/strawberry-topped-cheesecake.jpg",
								true, LocalDateTime.now(), LocalDateTime.now())
				));
			}
		};
	}
}
