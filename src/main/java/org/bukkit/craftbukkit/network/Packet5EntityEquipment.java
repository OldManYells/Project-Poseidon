package org.bukkit.craftbukkit.network;

import org.bukkit.craftbukkit.item.ItemStack;
import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5EntityEquipment extends Packet {

    public int entityId;
    public int slot;
    public int itemId;
    public int itemDamage;

    public Packet5EntityEquipment() {}

    public Packet5EntityEquipment(int i, int j, ItemStack itemstack) {
        this.entityId = i;
        this.slot = j;
        if (itemstack == null) {
            this.itemId = -1;
            this.itemDamage = 0;
        } else {
            this.itemId = itemstack.id;
            this.itemDamage = itemstack.getData();
        }
    }

    @Override
    public void a(DataInputStream datainputstream) throws IOException {
        this.readPacketData(datainputstream);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.entityId = datainputstream.readInt();
        this.slot = datainputstream.readShort();
        this.itemId = datainputstream.readShort();
        this.itemDamage = datainputstream.readShort();
    }

    @Override
    public void a(DataOutputStream dataoutputstream) throws IOException {
        this.writePacketData(dataoutputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.entityId);
        dataoutputstream.writeShort(this.slot);
        dataoutputstream.writeShort(this.itemId);
        dataoutputstream.writeShort(this.itemDamage);
    }

    @Override
    public void a(NetHandler nethandler) {
        this.handlePacket(nethandler);
    }

    public void handlePacket(NetHandler nethandler) {
        nethandler.a(this);
    }

    @Override
    public int a() {
        return this.getPacketSize();
    }

    public int getPacketSize() {
        return 8;
    }
}
