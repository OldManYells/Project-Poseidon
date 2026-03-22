package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet103SetSlot extends Packet {

    public int a;
    public int b;
    public ItemStack c;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet103SetSlot() {}

    public Packet103SetSlot(int i, int j, ItemStack itemstack) {
        this.a = i;
        this.b = j;
        this.c = itemstack == null ? itemstack : itemstack.cloneItemStack();
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet103Data packetData = packetDataCodec.readPacket103(datainputstream);
        this.a = packetData.getWindowId();
        this.b = packetData.getSlot();
        PacketDataCodec.ItemSlotData itemSlot = packetData.getItemSlot();
        if (itemSlot == null) {
            this.c = null;
        } else {
            this.c = new ItemStack(itemSlot.getItemId(), itemSlot.getCount(), itemSlot.getData());
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.ItemSlotData itemSlot = this.c == null
                ? null
                : new PacketDataCodec.ItemSlotData((short) this.c.id, this.c.count, (short) this.c.getData());
        packetDataCodec.writePacket103(
                new PacketDataCodec.Packet103Data(this.a, this.b, itemSlot),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet103Length();
    }
}
