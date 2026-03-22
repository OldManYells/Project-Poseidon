package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5EntityEquipment extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet5EntityEquipment() {}

    public Packet5EntityEquipment(int i, int j, ItemStack itemstack) {
        this.a = i;
        this.b = j;
        if (itemstack == null) {
            this.c = -1;
            this.d = 0;
        } else {
            this.c = itemstack.id;
            this.d = itemstack.getData();
        }
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet5Data packetData = packetDataCodec.readPacket5(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getSlot();
        this.c = packetData.getItemId();
        this.d = packetData.getDataValue();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket5(
                new PacketDataCodec.Packet5Data(this.a, this.b, this.c, this.d),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet5Length();
    }
}
