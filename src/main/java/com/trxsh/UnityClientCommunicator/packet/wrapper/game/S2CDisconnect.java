package com.trxsh.UnityClientCommunicator.packet.wrapper.game;

import com.trxsh.UnityClientCommunicator.packet.Packet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class S2CDisconnect extends Packet {


    public S2CDisconnect(BufferedReader in, BufferedWriter out) {
        super(in, out);
    }

    public String reason;

    @Override
    public void read() throws IOException {

        reason = readString();

    }

    @Override
    public void write() throws IOException {

        writeInteger(5052);
        writeString(reason);

    }

}
