package com.legacyminecraft.poseidon.runtime;


import java.util.List;

/**
 * Canonical world-save sequence used by legacy MinecraftServer wrappers.
 */
public final class ServerWorldSaveService {
    private static final ServerWorldSaveService INSTANCE = new ServerWorldSaveService();

    private ServerWorldSaveService() {
    }

    public static ServerWorldSaveService getInstance() {
        return INSTANCE;
    }

    public void saveWorldsAndEmitEvents(List<WorldServer> worlds, Server server) {
        for (int i = 0; i < worlds.size(); ++i) {
            WorldServer worldserver = worlds.get(i);
            worldserver.save(true, (IProgressUpdate) null);
            worldserver.saveLevel();

            WorldSaveEvent event = new WorldSaveEvent(worldserver.getWorld());
            server.getPluginManager().callEvent(event);
        }
    }

    public boolean shouldSavePlayersAfterWorldSave(boolean primaryWorldCanSave) {
        return !primaryWorldCanSave;
    }
}
