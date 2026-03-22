package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet19EntityAction extends Packet {

    public int a;
    public int animation;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet19EntityAction() {}

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet19Data packetData = packetDataCodec.readPacket19(datainputstream);
        this.a = packetData.getEntityId();
        this.animation = packetData.getAnimation();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket19(
                new PacketDataCodec.Packet19Data(this.a, this.animation),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet19Length();
    }
}
