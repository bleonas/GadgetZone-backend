package com.ecommerce.gadgetzone;

import com.ecommerce.gadgetzone.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GadgetzoneApplication implements CommandLineRunner {

    @Autowired
    private DatabaseConfig databaseConfig;  

    public static void main(String[] args) {
        SpringApplication.run(GadgetzoneApplication.class, args);  
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking database connection...");

        databaseConfig.checkDatabaseConnection();  
    }
}
