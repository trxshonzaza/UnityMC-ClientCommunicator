package com.trxsh.UnityClientCommunicator.packet.wrapper.login;

import com.trxsh.UnityClientCommunicator.packet.Packet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class C2SLoginStart extends Packet {

    public C2SLoginStart(BufferedReader in, BufferedWriter out) {
        super(in, out);
    }

    public String name;

    @Override
    public void read() throws IOException {

        name = readString();

    }

    @Override
    public void write() throws IOException {

    }

}
