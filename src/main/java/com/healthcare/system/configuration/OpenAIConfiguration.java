package com.healthcare.system.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class OpenAIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:9090");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Pratik Kumar, Ansh Makker");
        myContact.setEmail("pratik@gmail.com");

        Info information = new Info()
                .title("HealthCare Management System API")
                .version("1.0")
                .description("This API exposes endpoints to manage health care.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
