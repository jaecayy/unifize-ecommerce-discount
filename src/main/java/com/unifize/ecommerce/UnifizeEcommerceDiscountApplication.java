package com.unifize.ecommerce;

import com.unifize.ecommerce.model.*;
import com.unifize.ecommerce.orchestrator.MainOrchestrator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnifizeEcommerceDiscountApplication implements CommandLineRunner {

    private final MainOrchestrator orchestrator;

    public UnifizeEcommerceDiscountApplication(MainOrchestrator mainOrchestrator) {
        this.orchestrator = mainOrchestrator;
    }

	public static void main(String[] args) {
		SpringApplication.run(UnifizeEcommerceDiscountApplication.class, args);
	}

    @Override
    public void run(String... args) {
        orchestrator.runApp();
    }
}
