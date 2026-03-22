package com.legacyminecraft.poseidon.world;

import net.minecraft.server.IProgressUpdate;

public interface ConvertableContract {
    boolean canConvertWorld(String worldName);

    boolean convertWorld(String worldName, IProgressUpdate progressUpdate);
}
