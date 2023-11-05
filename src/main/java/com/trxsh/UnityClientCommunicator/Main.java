package com.trxsh.UnityClientCommunicator;

import com.trxsh.UnityClientCommunicator.ip.IPV4;
import com.trxsh.UnityClientCommunicator.server.TcpServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Main extends JavaPlugin {

    TcpServer server;

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("[UnityServer] initalizing server");

        try {
            server = new TcpServer(IPV4.getV4(), 7628);
            server.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getConsoleSender().sendMessage("[UnityServer] server started");

    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("[UnityServer] shutting down server");

        try {
            server.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getConsoleSender().sendMessage("[UnityServer] server stopped");

    }
}
