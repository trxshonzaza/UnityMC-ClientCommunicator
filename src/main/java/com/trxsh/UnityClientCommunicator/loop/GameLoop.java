package com.trxsh.UnityClientCommunicator.loop;

import com.trxsh.UnityClientCommunicator.client.Client;
import com.trxsh.UnityClientCommunicator.packet.Identity;
import com.trxsh.UnityClientCommunicator.packet.Packet;
import com.trxsh.UnityClientCommunicator.packet.wrapper.S2CRequestBlockPacket;
import com.trxsh.UnityClientCommunicator.packet.wrapper.game.S2CDisconnect;
import com.trxsh.UnityClientCommunicator.server.TcpServer;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;

public class GameLoop implements Runnable {

    public TcpServer server;

    public GameLoop(TcpServer server) {

        this.server = server;

    }

    public void loop() throws IOException {

        while(server.running) {

            Map<Client, InetAddress> synchronizedMap = Collections.synchronizedMap(TcpServer.serverInstance.clients);

            for(Client client : synchronizedMap.keySet()) {

                if(!client.connection.isConnected() || client.connection.isClosed()) {
                    Bukkit.getLogger().warning("Client " + client.uuid + " socket is closed. Removing connection");
                    TcpServer.serverInstance.connections.remove(client.connection);
                    TcpServer.serverInstance.clients.remove(client);
                }

                if(client.in.ready()) {

                    Bukkit.getLogger().info("There is data to be read!");

                    int id = Integer.parseInt(client.in.readLine());

                    Bukkit.getLogger().info("packet id is " + id);

                    Packet packet = Identity.identify(id);

                    assert packet != null;
                    packet.setReader(client.in);
                    packet.setWriter(client.out);

                    TcpServer.serverInstance.processor.add(packet, client);

                }

            }

            TcpServer.serverInstance.processor.process();

        }

    }

    @Override
    public void run() {
        try {
            while(true)
                loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
