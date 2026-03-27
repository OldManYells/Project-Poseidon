package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldStorageCompatGatewayRegistry;

final class PoseidonWorldStorageCompatGatewayBootstrap {
    private static volatile boolean installed;

    private PoseidonWorldStorageCompatGatewayBootstrap() {
    }

    static void ensureInstalled() {
        if (installed) {
            return;
        }
        synchronized (PoseidonWorldStorageCompatGatewayBootstrap.class) {
            if (installed) {
                return;
            }
            WorldStorageCompatGatewayRegistry.install(new PoseidonWorldStorageCompatGateway());
            installed = true;
        }
    }
}
