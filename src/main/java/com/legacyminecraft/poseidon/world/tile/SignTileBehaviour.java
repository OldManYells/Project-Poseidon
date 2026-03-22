package com.legacyminecraft.poseidon.world.tile;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet130UpdateSign;
import net.minecraft.server.TileEntitySign;

public final class SignTileBehaviour {
    private static final SignTileBehaviour INSTANCE = new SignTileBehaviour();
    private static final int MAX_LINE_LENGTH = 15;

    private SignTileBehaviour() {
    }

    public static SignTileBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeLines(NBTTagCompound tag, String[] lines) {
        tag.setString("Text1", lines[0]);
        tag.setString("Text2", lines[1]);
        tag.setString("Text3", lines[2]);
        tag.setString("Text4", lines[3]);
    }

    public void readLines(NBTTagCompound tag, String[] lines) {
        for (int i = 0; i < 4; ++i) {
            lines[i] = tag.getString("Text" + (i + 1));
            if (lines[i].length() > MAX_LINE_LENGTH) {
                lines[i] = lines[i].substring(0, MAX_LINE_LENGTH);
            }
        }
    }

    public Packet createUpdatePacket(TileEntitySign sign) {
        String[] packetLines = new String[4];

        for (int i = 0; i < 4; ++i) {
            packetLines[i] = sign.lines[i];
            if (sign.lines[i].length() > MAX_LINE_LENGTH) {
                packetLines[i] = sign.lines[i].substring(0, MAX_LINE_LENGTH);
            }
        }

        return new Packet130UpdateSign(sign.x, sign.y, sign.z, packetLines);
    }

    public boolean markReadOnlyOnLoad() {
        return false;
    }
}
