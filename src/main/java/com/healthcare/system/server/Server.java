package com.healthcare.system.server;

import com.healthcare.system.dependency.injection.Context;
import com.healthcare.system.server.request.handlers.*;
import com.sun.net.httpserver.HttpServer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(8000), 0);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Context.inject();
        DataSource dataSource = Context.dataSource;
        server.createContext("/doctor/register", new DoctorRegisterHandler());
        server.createContext("/healthProvider/register", new HealthProviderRegisterHandler());
        server.createContext("/patient/register", new PatientRegisterHandler());
        server.createContext("/nurse/register", new NurseRegisterHandler());
        server.createContext("/healthProvider/findAll",new HealthProviderFindAllHandler());
        server.createContext("/patient/findAll", new PatientFindAllHandler());
        server.createContext("/doctor/findAll", new DoctorFindAllHandler());
        server.createContext("/nurse/findAll", new NurseFindAllHandler());
        server.setExecutor(executor);
        server.start();
        System.out.println("Server started on port 8000");
    }
}
