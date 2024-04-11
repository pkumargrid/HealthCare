package com.healthcare.system.server.request.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.system.controllers.Controller;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.filters.Authentication;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class GenerateRegisterResponse {
    public static void generate(Controller<?> controller, ObjectMapper mapper, HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        try{
            ResponseCrudDTO<?> responseCrud = Authentication.isAuthenticated(controller);
            String jsonResponse = mapper.writeValueAsString(responseCrud);
            exchange.sendResponseHeaders(responseCrud.status, jsonResponse.length());

            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        } catch (AlreadyLoggedInException e) {
            exchange.sendResponseHeaders(409, e.getMessage().length());
            OutputStream os = exchange.getResponseBody();
            os.write(e.getMessage().getBytes());
            os.close();
        }
    }
}
