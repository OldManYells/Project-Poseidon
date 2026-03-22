package net.minecraft.server;

import com.legacyminecraft.poseidon.world.DataManagerContract;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IDataManager extends DataManagerContract {

    WorldData c();

    void b();

    IChunkLoader a(WorldProvider worldprovider);

    void a(WorldData worlddata, List list);

    void a(WorldData worlddata);

    PlayerFileData d();

    void e();

    File b(String s);

    UUID getUUID(); // CraftBukkit

    default WorldData loadWorldData() {
        return this.c();
    }

    default void verifySessionLock() {
        this.b();
    }

    default IChunkLoader getChunkLoader(WorldProvider worldProvider) {
        return this.a(worldProvider);
    }

    default void saveWorldDataWithPlayers(WorldData worldData, List playerData) {
        this.a(worldData, playerData);
    }

    default void saveWorldData(WorldData worldData) {
        this.a(worldData);
    }

    default PlayerFileData getPlayerFileData() {
        return this.d();
    }

    default void flush() {
        this.e();
    }

    default File getDataFile(String path) {
        return this.b(path);
    }

    default UUID getWorldUuid() {
        return this.getUUID();
    }
}
