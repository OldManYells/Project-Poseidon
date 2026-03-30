package org.bukkit.craftbukkit.network;

import org.bukkit.craftbukkit.item.ItemStack;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet102WindowClick extends Packet {

    public int windowId;
    public int slot;
    public int mouseButton;
    public short actionId;
    public ItemStack clickedItem;
    public boolean shiftHeld;

    public Packet102WindowClick() {}

    @Override
    public void a(NetHandler nethandler) {
        this.handlePacket(nethandler);
    }

    public void handlePacket(NetHandler nethandler) {
        nethandler.a(this);
    }

    @Override
    public void a(DataInputStream datainputstream) throws IOException {
        this.readPacketData(datainputstream);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.windowId = datainputstream.readByte();
        this.slot = datainputstream.readShort();
        this.mouseButton = datainputstream.readByte();
        this.actionId = datainputstream.readShort();
        this.shiftHeld = datainputstream.readBoolean();
        short short1 = datainputstream.readShort();

        if (short1 >= 0) {
            byte b0 = datainputstream.readByte();
            short short2 = datainputstream.readShort();

            this.clickedItem = new ItemStack(short1, b0, short2);
        } else {
            this.clickedItem = null;
        }
    }

    @Override
    public void a(DataOutputStream dataoutputstream) throws IOException {
        this.writePacketData(dataoutputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeByte(this.windowId);
        dataoutputstream.writeShort(this.slot);
        dataoutputstream.writeByte(this.mouseButton);
        dataoutputstream.writeShort(this.actionId);
        dataoutputstream.writeBoolean(this.shiftHeld);
        if (this.clickedItem == null) {
            dataoutputstream.writeShort(-1);
        } else {
            dataoutputstream.writeShort(this.clickedItem.id);
            dataoutputstream.writeByte(this.clickedItem.count);
            dataoutputstream.writeShort(this.clickedItem.getData());
        }
    }

    @Override
    public int a() {
        return this.getPacketSize();
    }

    public int getPacketSize() {
        return 11;
    }
}
