package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet102WindowClick extends Packet {

    public int a;
    public int b;
    public int c;
    public short d;
    public ItemStack e;
    public boolean f;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet102WindowClick() {}

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet102Data packetData = packetDataCodec.readPacket102(datainputstream);
        this.a = packetData.getWindowId();
        this.b = packetData.getSlot();
        this.c = packetData.getButton();
        this.d = packetData.getActionNumber();
        this.f = packetData.isShift();
        PacketDataCodec.ItemSlotData itemSlot = packetData.getItemSlot();
        if (itemSlot == null) {
            this.e = null;
        } else {
            this.e = new ItemStack(itemSlot.getItemId(), itemSlot.getCount(), itemSlot.getData());
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.ItemSlotData itemSlot = this.e == null
                ? null
                : new PacketDataCodec.ItemSlotData((short) this.e.id, this.e.count, (short) this.e.getData());
        packetDataCodec.writePacket102(
                new PacketDataCodec.Packet102Data(this.a, this.b, this.c, this.d, this.f, itemSlot),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet102Length();
    }
}
