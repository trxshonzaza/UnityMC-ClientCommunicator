package com.trxsh.UnityClientCommunicator.packet.wrapper;

import com.trxsh.UnityClientCommunicator.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class S2CRequestBlockPacket extends Packet {

    public S2CRequestBlockPacket(BufferedReader in, BufferedWriter out) {
        super(in, out);
    }

    int startX;
    int startY;
    int startZ;

    @Override
    public void read() throws IOException {

        startX = readInteger();
        startY = readInteger();
        startZ = readInteger();

    }

    @Override
    public void write() throws IOException {

        List<Block> blocks = new ArrayList<>();

        Location start = Bukkit.getWorlds().get(0).getBlockAt(startX, startY, startZ).getLocation();

        Chunk chunk = start.getChunk();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < start.getWorld().getMaxHeight(); y++) {
                    Block block = chunk.getBlock(x, y, z);

                    if(block.isLiquid() || block.getType() == Material.AIR)
                        continue;

                    blocks.add(block);

                }
            }
        }

        writeInteger(5051);
        writeInteger(blocks.size());

        for(Block block : blocks) {

            writeInteger(block.getX());
            writeInteger(block.getY());
            writeInteger(block.getZ());

        }

    }
}
