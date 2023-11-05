package com.trxsh.UnityClientCommunicator.packet.wrapper.login;

import com.trxsh.UnityClientCommunicator.packet.Packet;
import org.bukkit.Location;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.UUID;

public class S2CLoginSuccess extends Packet {

    public S2CLoginSuccess(BufferedReader in, BufferedWriter out) {
        super(in, out);
    }

    public String name;
    public UUID uuid;
    // Not needed now. public Location spawn;

    @Override
    public void read() throws IOException {

    }

    @Override
    public void write() throws IOException {

        writeString(name);
        writeString(uuid.toString());

        // Not needed.
        //writeDouble(spawn.getX());
        //writeDouble(spawn.getY());
        //writeDouble(spawn.getZ());

    }

}
