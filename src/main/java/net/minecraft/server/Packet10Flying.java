package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet10Flying extends Packet {

    public double x;
    public double y;
    public double z;
    public double stance;
    public float yaw;
    public float pitch;
    public boolean g;
    public boolean h;
    public boolean hasLook;
    protected final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet10Flying() {}

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.g = packetDataCodec.readPacket10(datainputstream).isOnGround();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket10(new PacketDataCodec.Packet10Data(this.g), dataoutputstream);
    }

    public int a() {
        return packetDataCodec.packet10Length();
    }
}
