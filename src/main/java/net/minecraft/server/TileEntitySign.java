package net.minecraft.server;

import org.bukkit.craftbukkit.network.Packet130UpdateSign;

public class TileEntitySign extends TileEntity {

    public String[] lines = new String[] { "", "", "", "" };
    public int b = -1; // editedLine
    private boolean editable = true;

    public TileEntitySign() {}

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("Text1", this.lines[0]);
        tag.setString("Text2", this.lines[1]);
        tag.setString("Text3", this.lines[2]);
        tag.setString("Text4", this.lines[3]);
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.editable = false;
        super.readFromNBT(tag);

        for (int lineIndex = 0; lineIndex < 4; ++lineIndex) {
            this.lines[lineIndex] = tag.getString("Text" + (lineIndex + 1));
            if (this.lines[lineIndex].length() > 15) {
                this.lines[lineIndex] = this.lines[lineIndex].substring(0, 15);
            }
        }
    }

    public Packet getUpdatePacket() {
        String[] packetLines = new String[4];

        for (int lineIndex = 0; lineIndex < 4; ++lineIndex) {
            packetLines[lineIndex] = this.lines[lineIndex];
            if (this.lines[lineIndex].length() > 15) {
                packetLines[lineIndex] = this.lines[lineIndex].substring(0, 15);
            }
        }

        return new Packet130UpdateSign(this.x, this.y, this.z, packetLines);
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Deprecated
    public void b(NBTTagCompound tag) {
        this.writeToNBT(tag);
    }

    @Deprecated
    public void a(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Deprecated
    public Packet f() {
        return this.getUpdatePacket();
    }

    @Deprecated
    public boolean a() {
        return this.isEditable();
    }

    @Deprecated
    public void a(boolean editable) {
        this.setEditable(editable);
    }
}
