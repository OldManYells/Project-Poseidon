package com.legacyminecraft.poseidon.world;

import net.minecraft.server.EntityHuman;

public interface PlayerFileDataContract {
    void savePlayerData(EntityHuman player);

    void loadPlayerData(EntityHuman player);
}
