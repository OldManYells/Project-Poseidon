package com.legacyminecraft.poseidon.runtime;


import java.util.List;

/**
 * Canonical world/player tick orchestration for the legacy runtime wrapper.
 */
public final class WorldTickOrchestrator {
    private static final WorldTickOrchestrator INSTANCE = new WorldTickOrchestrator();

    private WorldTickOrchestrator() {
    }

    public static WorldTickOrchestrator getInstance() {
        return INSTANCE;
    }

    public void tickWorlds(MinecraftServer server, int currentTick) {
        for (int worldIndex = 0; worldIndex < server.worlds.size(); ++worldIndex) {
            WorldServer worldserver = server.worlds.get(worldIndex);

            if (currentTick % 20 == 0) {
                for (int playerIndex = 0; playerIndex < worldserver.players.size(); ++playerIndex) {
                    EntityPlayer entityPlayer = (EntityPlayer) worldserver.players.get(playerIndex);
                    if (entityPlayer != null) {
                        entityPlayer.netServerHandler.sendPacket(new Packet4UpdateTime(entityPlayer.getPlayerTime()));
                    }
                }
            }

            worldserver.doTick();
            while (worldserver.doLighting()) {
                ;
            }
            worldserver.cleanUp();
        }
    }

    public void flushNetworkAndPlayerChunks(MinecraftServer server) {
        server.networkListenThread.a();
        server.serverConfigurationManager.b();
    }

    public void updateWorldTrackers(MinecraftServer server) {
        for (int worldIndex = 0; worldIndex < server.worlds.size(); ++worldIndex) {
            server.worlds.get(worldIndex).tracker.updatePlayers();
        }
    }

    public void tickUpdateBoxes(List updateBoxes) {
        for (int i = 0; i < updateBoxes.size(); ++i) {
            ((IUpdatePlayerListBox) updateBoxes.get(i)).a();
        }
    }
}
