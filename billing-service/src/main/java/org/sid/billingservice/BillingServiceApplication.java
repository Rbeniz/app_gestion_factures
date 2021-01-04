package org.sid.billingservice;

import org.sid.billingservice.rest.entities.Bill;
import org.sid.billingservice.rest.entities.ProductItem;
import org.sid.billingservice.rest.model.Customer;
import org.sid.billingservice.rest.model.Product;
import org.sid.billingservice.rest.repository.BillRepository;
import org.sid.billingservice.rest.repository.ProductItemRepository;
import org.sid.billingservice.rest.service.CustomerRestClient;
import org.sid.billingservice.rest.service.ProductRestClient;

import org.sid.billingservice.rest.web.BillingRestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.PagedModel;

import javax.management.MXBean;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "org.sid.billingservice.rest" )
public class BillingServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductRestClient productRestClient){
		return args -> {
			Customer customer = customerRestClient.getCustomerById(1L);
			Bill bill1 = billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
			PagedModel<Product> productPagedModel = productRestClient.pageProducts();
			productPagedModel.forEach(p->{
				ProductItem productItem = new ProductItem();
				productItem.setPrice(p.getPrice());
				productItem.setQuantity(1+ new Random().nextInt(100));
				productItem.setBill(bill1);
				productItem.setProductId(p.getId());
				productItemRepository.save(productItem);
			});
			System.out.println(customer.getId());
			System.out.println(customer.getName());
			System.out.println(customer.getEmail());

		};
	}

}
