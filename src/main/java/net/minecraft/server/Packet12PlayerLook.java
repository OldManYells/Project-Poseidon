package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet12PlayerLook extends Packet10Flying {

    public Packet12PlayerLook() {
        this.hasLook = true;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet12Data packetData = PacketDataCodec.getInstance().readPacket12(datainputstream);
        this.yaw = packetData.getYaw();
        this.pitch = packetData.getPitch();
        this.g = packetData.isOnGround();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.getInstance().writePacket12(
                new PacketDataCodec.Packet12Data(this.yaw, this.pitch, this.g),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet12Length();
    }
}
