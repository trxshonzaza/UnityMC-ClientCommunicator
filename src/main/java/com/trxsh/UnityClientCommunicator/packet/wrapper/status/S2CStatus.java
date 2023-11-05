package com.trxsh.UnityClientCommunicator.packet.wrapper.status;

import com.trxsh.UnityClientCommunicator.packet.Packet;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class S2CStatus extends Packet {

    public S2CStatus(BufferedReader in, BufferedWriter out) {
        super(in, out);
    }

    @Override
    public void read() throws IOException {

    }

    @Override
    public void write() throws IOException {

        writeInteger(Bukkit.getOnlinePlayers().size());
        writeInteger(Bukkit.getMaxPlayers());
        writeString(Bukkit.getMotd());
        writeString(Bukkit.getBukkitVersion());

    }

}
