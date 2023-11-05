package com.trxsh.UnityClientCommunicator.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

public class Client {

    public Socket connection;
    public InetAddress clientAddress;
    public String name;
    public UUID uuid;

    public BufferedReader in;
    public BufferedWriter out;

    public Client(Socket socket, InetAddress addr, String name, UUID uuid, BufferedReader in, BufferedWriter out) {

        this.connection = socket;
        this.clientAddress = addr;
        this.name = name;
        this.uuid = uuid;
        this.in = in;
        this.out = out;

    }

}
