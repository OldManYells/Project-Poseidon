package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet16BlockItemSwitch extends Packet {

    public int itemInHandIndex;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet16BlockItemSwitch() {}

    public void a(DataInputStream datainputstream) throws IOException {
        this.itemInHandIndex = packetDataCodec.readPacket16(datainputstream).getItemInHandIndex();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket16(
                new PacketDataCodec.Packet16Data(this.itemInHandIndex),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet16Length();
    }
}
