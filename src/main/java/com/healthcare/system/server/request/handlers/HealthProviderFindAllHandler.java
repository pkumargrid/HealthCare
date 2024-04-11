package com.healthcare.system.server.request.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.system.controllers.HealthProviderController;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.dependency.injection.Context;
import com.healthcare.system.entities.HealthProvider;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class HealthProviderFindAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        ResponseCrudDTO<List<HealthProvider>> responseCrud = new HealthProviderController(Context.healthProviderService).findAll();
        String jsonResponse = mapper.writeValueAsString(responseCrud);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCrud.status, jsonResponse.length());

        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();
    }
}
