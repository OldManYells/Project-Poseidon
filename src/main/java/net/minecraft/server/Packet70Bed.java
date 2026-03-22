package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet70Bed extends Packet {

    public static final String[] a = new String[] { "tile.bed.notValid", null, null};
    public int b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet70Bed() {}

    public Packet70Bed(int i) {
        this.b = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.b = packetDataCodec.readPacket70Bed(datainputstream).getEventId();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket70Bed(new PacketDataCodec.Packet70BedData(this.b), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet70Length();
    }
}
