package com.healthcare.system.server.request.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.system.controllers.PatientController;
import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.dependency.injection.Context;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class PatientRegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        PatientDTO requestObject = mapper.readValue(requestBody, PatientDTO.class);

        ResponseCrudDTO<Void> responseCrud = new PatientController(Context.patientService).save(requestObject);
        String jsonResponse = mapper.writeValueAsString(responseCrud);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCrud.status, jsonResponse.length());

        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();
    }
}
