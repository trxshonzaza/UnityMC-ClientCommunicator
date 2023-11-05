package com.trxsh.UnityClientCommunicator.processor;

import com.trxsh.UnityClientCommunicator.client.Client;
import com.trxsh.UnityClientCommunicator.packet.Packet;
import com.trxsh.UnityClientCommunicator.packet.wrapper.S2CRequestBlockPacket;
import com.trxsh.UnityClientCommunicator.packet.wrapper.game.S2CDisconnect;
import com.trxsh.UnityClientCommunicator.server.TcpServer;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.*;

public class PacketProcessor {

    public HashMap<Packet, Client> packetsToProcess = new HashMap<>();
    public TcpServer server;

    public PacketProcessor(TcpServer server) {
        this.server = server;
    }

    public void process() throws IOException {

        Map<Packet, Client> synchronizedMap = Collections.synchronizedMap(packetsToProcess);

        if(!Collections.synchronizedMap(packetsToProcess).keySet().isEmpty()) {

            Packet packet = (Packet) synchronizedMap.keySet().toArray()[0];

            Client sender = synchronizedMap.get(packet);

            if(packet == null) {
                Bukkit.getLogger().warning("packet to be processed is null...");
                packetsToProcess.remove(null);
                return;
            }

            if(sender == null) {
                Bukkit.getLogger().warning("sender of packet to be processed is null...");
                packetsToProcess.remove(packet);
                return;
            }

            if(packet.getClass().equals(S2CRequestBlockPacket.class)) {

                packet.read();
                packet.write();
                packet.send();

                Bukkit.getLogger().info("read and sent packet successfully.");

            }

            if(packet.getClass().equals(S2CDisconnect.class)) {

                packet.read();

                String reason = ((S2CDisconnect) packet).reason;

                Bukkit.getLogger().info("Unity client " + sender.name + " is disconnecting for reason " + reason);

                TcpServer.serverInstance.connections.remove(sender.connection);

                sender.connection.close();

                TcpServer.serverInstance.clients.remove(sender);

            }

            packetsToProcess.remove(packet);

        }

    }

    public void add(Packet packet, Client client) {
        packetsToProcess.put(packet, client);
    }

}
