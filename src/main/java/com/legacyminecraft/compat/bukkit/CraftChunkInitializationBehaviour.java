package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunk constructor initialization from handle state.
 */
public final class CraftChunkInitializationBehaviour {
    private static final CraftChunkInitializationBehaviour INSTANCE = new CraftChunkInitializationBehaviour();
    private static final WorldServerProjectionBridgeBehaviour WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR =
            WorldServerProjectionBridgeBehaviour.getInstance();

    private CraftChunkInitializationBehaviour() {
    }

    public static CraftChunkInitializationBehaviour getInstance() {
        return INSTANCE;
    }

    public InitializationState initialize(net.minecraft.server.Chunk chunkHandle) {
        return new InitializationState(
                WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveWorldServer(chunkHandle.world),
                chunkHandle.x,
                chunkHandle.z
        );
    }

    public static final class InitializationState {
        private final net.minecraft.server.WorldServer worldServer;
        private final int x;
        private final int z;

        public InitializationState(net.minecraft.server.WorldServer worldServer, int x, int z) {
            this.worldServer = worldServer;
            this.x = x;
            this.z = z;
        }

        public net.minecraft.server.WorldServer getWorldServer() {
            return worldServer;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
    }
}
