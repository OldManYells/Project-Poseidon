package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet15Place extends Packet {

    public int a;
    public int b;
    public int c;
    public int face;
    public ItemStack itemstack;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet15Place() {}

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet15Data packetData = packetDataCodec.readPacket15(datainputstream);
        this.a = packetData.getX();
        this.b = packetData.getY();
        this.c = packetData.getZ();
        this.face = packetData.getFace();
        PacketDataCodec.ItemSlotData itemSlot = packetData.getItemSlot();
        if (itemSlot == null) {
            this.itemstack = null;
        } else {
            this.itemstack = new ItemStack(itemSlot.getItemId(), itemSlot.getCount(), itemSlot.getData());
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.ItemSlotData itemSlot = this.itemstack == null
                ? null
                : new PacketDataCodec.ItemSlotData((short) this.itemstack.id, this.itemstack.count, (short) this.itemstack.getData());
        packetDataCodec.writePacket15(
                new PacketDataCodec.Packet15Data(this.a, this.b, this.c, this.face, itemSlot),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet15Length();
    }
}
