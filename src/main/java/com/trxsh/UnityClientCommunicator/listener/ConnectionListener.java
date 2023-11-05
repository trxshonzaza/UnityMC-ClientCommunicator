package com.trxsh.UnityClientCommunicator.listener;

import com.trxsh.UnityClientCommunicator.client.Client;
import com.trxsh.UnityClientCommunicator.packet.wrapper.login.C2SHandshake;
import com.trxsh.UnityClientCommunicator.packet.wrapper.login.C2SLoginStart;
import com.trxsh.UnityClientCommunicator.packet.wrapper.login.S2CLoginSuccess;
import com.trxsh.UnityClientCommunicator.packet.wrapper.status.S2CStatus;
import com.trxsh.UnityClientCommunicator.server.TcpServer;
import com.trxsh.UnityClientCommunicator.stat.Status;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class ConnectionListener implements Runnable {

    ServerSocket server;

    public ConnectionListener(ServerSocket socket) {

        this.server = socket;

    }

    public void listenForConnections() throws IOException {

        Socket socket = server.accept();

        try {

            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);

            String addr = socket.getInetAddress().toString();

            Bukkit.getLogger().info("[UnityServer] incoming connection from " + addr + "!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            C2SHandshake handshake = new C2SHandshake(in, out);

            handshake.read();

            String username = "";
            UUID clientId = UUID.randomUUID();

            if(handshake.status == Status.STATUS) {

                Bukkit.getLogger().info("[UnityServer] " + addr + " is requesting server status. disconnecting...");

                S2CStatus status = new S2CStatus(in, out);

                status.write();
                status.send();

                socket.close();

                return;

            }

            if(handshake.status == Status.LOGIN) {

                Bukkit.getLogger().info("[UnityServer] " + addr + " is requesting to log in");

                C2SLoginStart loginStart = new C2SLoginStart(in, out);

                loginStart.read();

                username = loginStart.name;

                Bukkit.getLogger().info("[UnityServer] username is " + username);
                Bukkit.getLogger().info("[UnityServer] client id is " + clientId);

                S2CLoginSuccess loginSuccess = new S2CLoginSuccess(in, out);

                loginSuccess.name = username;
                loginSuccess.uuid = clientId;

                loginSuccess.write();
                loginSuccess.send();

                Bukkit.getLogger().info("[UnityServer] unity client with username " + username + " (IP: " + addr + ") with client ID " + clientId + " has successfully logged into the server!");

                Client client = new Client(socket, socket.getInetAddress(), username, clientId, in, out);

                TcpServer.serverInstance.connections.add(socket);
                TcpServer.serverInstance.clients.put(client, socket.getInetAddress());

            }

        }catch(Exception e) {
            Bukkit.getLogger().warning("Failed auth from UnityServer! is the client genuine?");
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while(true)
                listenForConnections();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
