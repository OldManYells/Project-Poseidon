package com.legacyminecraft.poseidon.world.tile;

import net.minecraft.server.NBTTagCompound;

public final class JukeboxTileBehaviour {
    private static final JukeboxTileBehaviour INSTANCE = new JukeboxTileBehaviour();

    private JukeboxTileBehaviour() {
    }

    public static JukeboxTileBehaviour getInstance() {
        return INSTANCE;
    }

    public int readRecordId(NBTTagCompound tag) {
        return tag.e("Record");
    }

    public void writeRecordIdIfPresent(NBTTagCompound tag, int recordId) {
        if (recordId > 0) {
            tag.a("Record", recordId);
        }
    }
}
