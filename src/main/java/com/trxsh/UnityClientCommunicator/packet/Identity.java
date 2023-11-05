package com.trxsh.UnityClientCommunicator.packet;

import com.trxsh.UnityClientCommunicator.packet.wrapper.S2CRequestBlockPacket;
import com.trxsh.UnityClientCommunicator.packet.wrapper.game.S2CDisconnect;

import java.util.HashMap;

public class Identity {

    public static HashMap<Integer, Packet> packets = new HashMap<>();
    static {
        packets.put(5051, new S2CRequestBlockPacket(null, null));
        packets.put(5052, new S2CDisconnect(null, null));
    }

    public static Packet identify(int id) {
        for(int id1 : packets.keySet()) {
            if(id == id1) {
                return packets.get(id);
            }
        }

        return null;
    }

}
