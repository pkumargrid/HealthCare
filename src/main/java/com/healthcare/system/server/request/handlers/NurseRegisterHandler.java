package com.healthcare.system.server.request.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.system.controllers.NurseController;
import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.dependency.injection.Context;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class NurseRegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        NurseDTO requestObject = mapper.readValue(requestBody, NurseDTO.class);

        ResponseCrudDTO<Void> responseCrud = new NurseController(Context.nurseService).save(requestObject);
        String jsonResponse = mapper.writeValueAsString(responseCrud);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCrud.status, jsonResponse.length());

        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();
    }
}
