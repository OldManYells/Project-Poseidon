package org.bukkit.craftbukkit.network;

import org.bukkit.craftbukkit.item.ItemStack;
import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet15Place extends Packet {

    public int blockX;
    public int blockY;
    public int blockZ;
    public int face;
    public ItemStack itemstack;

    public Packet15Place() {}

    @Override
    public void a(DataInputStream datainputstream) throws IOException {
        this.readPacketData(datainputstream);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.blockX = datainputstream.readInt();
        this.blockY = datainputstream.read();
        this.blockZ = datainputstream.readInt();
        this.face = datainputstream.read();
        short short1 = datainputstream.readShort();

        if (short1 >= 0) {
            byte b0 = datainputstream.readByte();
            short short2 = datainputstream.readShort();

            this.itemstack = new ItemStack(short1, b0, short2);
        } else {
            this.itemstack = null;
        }
    }

    @Override
    public void a(DataOutputStream dataoutputstream) throws IOException {
        this.writePacketData(dataoutputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.blockX);
        dataoutputstream.write(this.blockY);
        dataoutputstream.writeInt(this.blockZ);
        dataoutputstream.write(this.face);
        if (this.itemstack == null) {
            dataoutputstream.writeShort(-1);
        } else {
            dataoutputstream.writeShort(this.itemstack.id);
            dataoutputstream.writeByte(this.itemstack.count);
            dataoutputstream.writeShort(this.itemstack.getData());
        }
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
        return 15;
    }
}
