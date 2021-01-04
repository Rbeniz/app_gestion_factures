package org.sid.inventoryservice;

import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(InventoryRepository inventoryRepository, RepositoryRestConfiguration restConfiguration){

		restConfiguration.exposeIdsFor(Product.class);
		return args -> {
			inventoryRepository.save(new Product(null,"Ordinateur",2000,58));
			inventoryRepository.save(new Product(null,"Imprimante",3500,300));
			inventoryRepository.save(new Product(null,"Disque dur",5500,275));
		};

	}

}
