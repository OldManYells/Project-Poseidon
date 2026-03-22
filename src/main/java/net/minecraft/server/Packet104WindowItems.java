package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet104WindowItems extends Packet {

    public int a;
    public ItemStack[] b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet104WindowItems() {}

    public Packet104WindowItems(int i, List list) {
        this.a = i;
        this.b = new ItemStack[list.size()];

        for (int j = 0; j < this.b.length; ++j) {
            ItemStack itemstack = (ItemStack) list.get(j);

            this.b[j] = itemstack == null ? null : itemstack.cloneItemStack();
        }
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet104Data packetData = packetDataCodec.readPacket104(datainputstream);
        this.a = packetData.getWindowId();
        PacketDataCodec.ItemSlotData[] slots = packetData.getSlots();
        this.b = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; ++i) {
            if (slots[i] != null) {
                this.b[i] = new ItemStack(slots[i].getItemId(), slots[i].getCount(), slots[i].getData());
            }
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.ItemSlotData[] slots = new PacketDataCodec.ItemSlotData[this.b.length];
        for (int i = 0; i < this.b.length; ++i) {
            if (this.b[i] != null) {
                slots[i] = new PacketDataCodec.ItemSlotData((short) this.b[i].id, this.b[i].count, (short) this.b[i].getData());
            }
        }
        packetDataCodec.writePacket104(new PacketDataCodec.Packet104Data(this.a, slots), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet104Length(this.b.length);
    }
}
