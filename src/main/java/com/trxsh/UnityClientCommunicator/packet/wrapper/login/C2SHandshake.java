package com.trxsh.UnityClientCommunicator.packet.wrapper.login;

import com.trxsh.UnityClientCommunicator.packet.Packet;
import com.trxsh.UnityClientCommunicator.stat.Status;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class C2SHandshake extends Packet {


    public C2SHandshake(BufferedReader in, BufferedWriter out) {
        super(in, out);
    }

    public Status status;

    @Override
    public void read() throws IOException {

        status = Status.values()[readInteger()];

    }

    @Override
    public void write() throws IOException {

    }

}
