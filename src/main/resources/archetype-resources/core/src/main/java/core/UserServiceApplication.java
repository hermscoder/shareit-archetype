package ${package}.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"${package}.data"})
@EntityScan(basePackages = {"${package}.domain"})
@ComponentScan(basePackages = {"${package}.data", "${package}.presentation", "${package}.service", "${package}.utils", "${package}.infrastructure"})
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }
}
