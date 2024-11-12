package com.example;

import java.io.*;
import java.net.*;

public class DualServer {

    // HTTP Server to listen on port 2080
    public static void startHttpServer() {
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = Integer.parseInt((httpPortStr != null) ? httpPortStr : "2080");
        try (ServerSocket serverSocket = new ServerSocket(httpPort)) {
            System.out.println("HTTP Server is listening on port " + httpPort + "...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = in.readLine();
                    System.out.println("Received HTTP request: " + line);

                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/plain");
                    out.println();
                    out.println("Hello from the HTTP server!");
                } catch (IOException e) {
                    System.err.println("Error handling HTTP request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("HTTP Server error: " + e.getMessage());
        }
    }

    // TCP Server to listen on port 2021
    public static void startTcpServer() {
        String tcpPortStr = System.getenv("TCP_PORT");
        int tcpPort = Integer.parseInt((tcpPortStr != null) ? tcpPortStr : "2021");
        try (ServerSocket serverSocket = new ServerSocket(tcpPort)) {
            System.out.println("TCP Server is listening on port" + tcpPort + "...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    InetAddress clientAddress = socket.getInetAddress();
                    String clientIp = clientAddress.getHostAddress();
                    System.out.println("New connection from " + clientIp);

                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Your IP address is: " + clientIp);
                } catch (IOException e) {
                    System.err.println("Error handling TCP connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("TCP Server error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Start HTTP server in a separate thread
        Thread httpThread = new Thread(DualServer::startHttpServer);
        httpThread.start();

        // Start TCP server in a separate thread
        Thread tcpThread = new Thread(DualServer::startTcpServer);
        tcpThread.start();
    }
}
