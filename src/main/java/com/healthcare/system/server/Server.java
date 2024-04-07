package com.healthcare.system.server;

import com.healthcare.system.dependency.injection.Injector;
import com.healthcare.system.server.request.handlers.DoctorRegisterHandler;
import com.healthcare.system.server.request.handlers.HealthProviderRegisterHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(8000), 0);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Injector.inject();
        server.createContext("/doctor/register", new DoctorRegisterHandler());
        server.createContext("/healthProvider/register", new HealthProviderRegisterHandler());
        server.setExecutor(executor);
        server.start();
        System.out.println("Server started on port 8000");
    }
}
