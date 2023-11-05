package com.trxsh.UnityClientCommunicator.packet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Packet {

    private BufferedReader in;
    private BufferedWriter out;

    public Packet(BufferedReader in, BufferedWriter out) {

        this.in = in;
        this.out = out;

    }

    public int writeInteger(int v) throws IOException {

        out.write(new String(v + "\n"));

        return v;

    }

    public int readInteger() throws IOException {

        int v = Integer.parseInt(in.readLine());

        return v;

    }

    public double readDouble() throws IOException {

        double v = Double.parseDouble(in.readLine());

        return v;

    }

    public double writeDouble(double v) throws IOException {

        out.write(new String(v + "\n"));

        return v;

    }

    public byte writeByte(byte v) throws IOException {

        out.write(new String(v + "\n"));

        return v;

    }

    public byte readByte() throws IOException {

        byte v = Byte.parseByte(in.readLine());

        return v;
    }

    public String writeString(String v) throws IOException {

        out.write(v   + "\n");

        return v;

    }

    public String readString() throws IOException {

        return in.readLine();

    }

    public boolean writeBoolean(boolean v) throws IOException {

        out.write(new String(v + "\n"));

        return v;

    }

    public boolean readBoolean() throws IOException {

        return Boolean.parseBoolean(in.readLine());

    }

    public short writeShort(short v) throws IOException {

        out.write(new String(v + "\n"));

        return v;

    }

    public short readShort() throws IOException {

        short v = Short.parseShort(in.readLine());

        return v;

    }


    public void send() throws IOException {
        out.flush();
    }

    public void setReader(BufferedReader v) {
        in = v;
    }

    public void setWriter(BufferedWriter v) {
        out = v;
    }

    public abstract void read() throws IOException;

    public abstract void write() throws IOException;

}