package com.pshekhar.securitymodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SecurityModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityModuleApplication.class, args);
    }

}
