package net.minecraft.server;

import com.legacyminecraft.poseidon.world.PlayerDataStoragePolicy;
import com.legacyminecraft.poseidon.world.PlayerNbtStorageSystem;
import com.legacyminecraft.poseidon.world.SessionLockManager;
import com.legacyminecraft.poseidon.world.WorldDataPersistence;
import com.legacyminecraft.poseidon.world.WorldIdentityStore;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class PlayerNBTManager implements PlayerFileData, IDataManager {

    private static final Logger a = Logger.getLogger("Minecraft");
    private final File b;
    private final File c;
    private final File d;
    private final long e = System.currentTimeMillis();
    private final PlayerDataStoragePolicy playerDataStoragePolicy = PlayerDataStoragePolicy.getInstance();
    private final SessionLockManager sessionLockManager = SessionLockManager.getInstance();
    private final WorldDataPersistence worldDataPersistence = WorldDataPersistence.getInstance();
    private final WorldIdentityStore worldIdentityStore = WorldIdentityStore.getInstance();
    private final PlayerNbtStorageSystem playerNbtStorageSystem = PlayerNbtStorageSystem.getInstance();
    private UUID uuid = null; // CraftBukkit

    public PlayerNBTManager(File file1, String s, boolean flag) {
        this.b = new File(file1, s);
        this.c = new File(this.b, "players");
        this.d = new File(this.b, "data");
        playerNbtStorageSystem.ensureStorageDirectories(this.b, this.c, this.d, flag);

        this.f();
    }

    private void f() {
        sessionLockManager.writeSessionLock(this.b, this.e);
    }

    protected File a() {
        return this.b;
    }

    public void b() {
        sessionLockManager.verifySessionLock(this.b, this.e);
    }

    public IChunkLoader a(WorldProvider worldprovider) {
        return new ChunkLoader(playerNbtStorageSystem.resolveChunkStorageDirectory(this.b, worldprovider), true);
    }

    public WorldData c() {
        return worldDataPersistence.loadWorldData(this.b);
    }

    public void a(WorldData worlddata, List list) {
        worldDataPersistence.saveWorldDataWithPlayerList(this.b, worlddata, list);
    }

    public void a(WorldData worlddata) {
        worldDataPersistence.saveWorldData(this.b, worlddata);
    }

    public void a(EntityHuman entityhuman) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        entityhuman.d(nbttagcompound);

        playerNbtStorageSystem.savePlayerData(
                this.c,
                playerDataStoragePolicy.getPlayerDataFile(this.c, entityhuman.name),
                entityhuman.name,
                nbttagcompound,
                a
        );
    }

    public void b(EntityHuman entityhuman) {
        NBTTagCompound nbttagcompound = this.a(entityhuman.name);

        if (nbttagcompound != null) {
            entityhuman.e(nbttagcompound);
        }
    }

    public NBTTagCompound a(String s) {
        try {
            playerDataStoragePolicy.migrateNamedDataToUuidStorageIfRequired(this.c, s);
            File file1 = playerDataStoragePolicy.getPlayerDataFile(this.c, s);
            return playerNbtStorageSystem.loadPlayerData(file1, s, a);
        } catch (Exception exception) {
            a.warning("Failed to load player data for " + s);
        }

        return null;
    }

    public PlayerFileData d() {
        return this;
    }

    public void e() {
    }

    public File b(String s) {
        return new File(this.d, s + ".dat");
    }

    // CraftBukkit start
    public UUID getUUID() {
        if (uuid != null) return uuid;
        try {
            uuid = worldIdentityStore.loadOrCreateWorldUuid(this.b);
            return uuid;
        } catch (IOException ex) {
            return null;
        }
    }
    // CraftBukkit end
}
