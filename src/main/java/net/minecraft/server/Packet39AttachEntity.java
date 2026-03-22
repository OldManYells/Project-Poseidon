package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet39AttachEntity extends Packet {

    public int a;
    public int b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet39AttachEntity() {}

    public Packet39AttachEntity(Entity entity, Entity entity1) {
        this.a = entity.id;
        this.b = entity1 != null ? entity1.id : -1;
    }

    public int a() {
        return packetDataCodec.packet39Length();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet39Data packetData = packetDataCodec.readPacket39(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getVehicleId();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket39(new PacketDataCodec.Packet39Data(this.a, this.b), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }
}
