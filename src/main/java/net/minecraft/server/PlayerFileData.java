package net.minecraft.server;

import com.legacyminecraft.poseidon.world.PlayerFileDataContract;

public interface PlayerFileData extends PlayerFileDataContract {

    void a(EntityHuman entityhuman);

    void b(EntityHuman entityhuman);

    default void savePlayerData(EntityHuman player) {
        this.a(player);
    }

    default void loadPlayerData(EntityHuman player) {
        this.b(player);
    }
}
