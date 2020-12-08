package com.buderus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.Collections;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "file:${user.home}/conf/application-buderus.properties",  ignoreResourceNotFound = true)
})
public class BuderusApplication {

    public static void main(String[] args) {
        System.out.println("Home path: "+ System.getProperty("user.home"));
        SpringApplication app = new SpringApplication(BuderusApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
}
