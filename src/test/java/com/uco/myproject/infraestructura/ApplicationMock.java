package com.uco.myproject.infraestructura;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.uco"})
@EnableJpaRepositories(basePackages = "com.uco")
public class ApplicationMock {

}
