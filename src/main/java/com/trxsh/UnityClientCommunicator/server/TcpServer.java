package com.trxsh.UnityClientCommunicator.server;

import com.trxsh.UnityClientCommunicator.client.Client;
import com.trxsh.UnityClientCommunicator.listener.ConnectionListener;
import com.trxsh.UnityClientCommunicator.loop.GameLoop;
import com.trxsh.UnityClientCommunicator.processor.PacketProcessor;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TcpServer {

    private InetAddress ip;
    private int port;

    public boolean running = false;

    private ServerSocket server;

    public int maxPlayers = 32;

    public List<Socket> connections = new ArrayList<Socket>();
    public Map<Client, InetAddress> clients = new HashMap<Client, InetAddress>(maxPlayers);
    public static TcpServer serverInstance;

    public PacketProcessor processor;

    public TcpServer(InetAddress ip, int port) throws IOException {

        this.ip = ip;
        this.port = port;
        serverInstance = this;

    }

    public void init() throws IOException {
        try {

            System.out.println("preparing server");

            server = new ServerSocket(port, 1, ip);

            processor = new PacketProcessor(this);

            Thread connectionThread = new Thread(new ConnectionListener(server));
            connectionThread.start();

            running = true;

            Thread serverLoopThread = new Thread(new GameLoop(this));
            serverLoopThread.start();

            Bukkit.getLogger().info("[UnityServer] Server started on ip " + ip.getHostAddress() + " and port " + port);

        }catch(IOException e) {
            throw e;
        }
    }

    public void shutdown() throws IOException {
        for(Socket socket : connections) {
            socket.close();
        }

        connections.clear();
        clients.clear();

        if(server != null) {
            server.close();
        }
    }

}
