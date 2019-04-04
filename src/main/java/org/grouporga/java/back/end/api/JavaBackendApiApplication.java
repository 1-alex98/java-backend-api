package org.grouporga.java.back.end.api;

import org.grouporga.java.back.end.api.config.OrgaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(OrgaProperties.class)
public class JavaBackendApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBackendApiApplication.class, args);
    }

}
