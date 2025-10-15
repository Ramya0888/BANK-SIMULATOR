package com.simulator.bank.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class BankApplication extends ResourceConfig {
    public BankApplication() {
        // Scan all resource packages
        packages("com.simulator.bank.api");

        // Register JSON and CORS
        register(JacksonFeature.class);
        register(CORSFilter.class);

        System.out.println(" BankApplication initialized and CORS filter registered");
         
    }
}
