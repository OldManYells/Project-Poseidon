package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet11PlayerPosition extends Packet10Flying {

    public Packet11PlayerPosition() {
        this.h = true;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet11Data packetData = PacketDataCodec.getInstance().readPacket11(datainputstream);
        this.x = packetData.getX();
        this.y = packetData.getY();
        this.stance = packetData.getStance();
        this.z = packetData.getZ();
        this.g = packetData.isOnGround();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.getInstance().writePacket11(
                new PacketDataCodec.Packet11Data(this.x, this.y, this.stance, this.z, this.g),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet11Length();
    }
}
