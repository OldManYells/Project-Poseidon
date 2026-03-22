package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet13PlayerLookMove extends Packet10Flying {

    public Packet13PlayerLookMove() {
        this.hasLook = true;
        this.h = true;
    }

    public Packet13PlayerLookMove(double d0, double d1, double d2, double d3, float f, float f1, boolean flag) {
        this.x = d0;
        this.y = d1;
        this.stance = d2;
        this.z = d3;
        this.yaw = f;
        this.pitch = f1;
        this.g = flag;
        this.hasLook = true;
        this.h = true;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet13Data packetData = PacketDataCodec.getInstance().readPacket13(datainputstream);
        this.x = packetData.getX();
        this.y = packetData.getY();
        this.stance = packetData.getStance();
        this.z = packetData.getZ();
        this.yaw = packetData.getYaw();
        this.pitch = packetData.getPitch();
        this.g = packetData.isOnGround();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.getInstance().writePacket13(
                new PacketDataCodec.Packet13Data(this.x, this.y, this.stance, this.z, this.yaw, this.pitch, this.g),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet13Length();
    }
}
