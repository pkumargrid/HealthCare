package com.healthcare.system.server;

import com.healthcare.system.dependency.injection.Injector;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(8000), 0);

        Injector.inject();

        // Start the server
        server.start();
        System.out.println("Server started on port 8000");
    }
}
