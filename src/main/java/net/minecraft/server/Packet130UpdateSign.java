package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet130UpdateSign extends Packet {

    public int x;
    public int y;
    public int z;
    public String[] lines;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet130UpdateSign() {
        this.k = true;
    }

    public Packet130UpdateSign(int i, int j, int k, String[] astring) {
        this.k = true;
        this.x = i;
        this.y = j;
        this.z = k;
        this.lines = astring;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet130Data packetData = packetDataCodec.readPacket130(datainputstream);
        this.x = packetData.getX();
        this.y = packetData.getY();
        this.z = packetData.getZ();
        this.lines = packetData.getLines();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket130(
                new PacketDataCodec.Packet130Data(this.x, this.y, this.z, this.lines),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet130Length(this.lines);
    }
}
