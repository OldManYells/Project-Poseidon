package com.legacyminecraft.poseidon.world;


public interface PlayerFileDataContract {
    void savePlayerData(EntityHuman player);

    void loadPlayerData(EntityHuman player);
}
