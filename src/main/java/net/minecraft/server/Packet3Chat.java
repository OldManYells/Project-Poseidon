package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat extends Packet {

    public String message;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet3Chat() {}

    public Packet3Chat(String s) {
        /* CraftBukkit start - handle this later
        if (s.length() > 119) {
            s = s.substring(0, 119);
        }
        // CraftBukkit end */

        this.message = s;
    }

    public void a(DataInputStream datainputstream) throws IOException { // CraftBukkit
        this.message = packetDataCodec.readPacket3(datainputstream).getMessage();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException { // CraftBukkit
        packetDataCodec.writePacket3(new PacketDataCodec.Packet3Data(this.message), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet3Length(this.message);
    }
}
