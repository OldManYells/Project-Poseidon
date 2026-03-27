package net.minecraft.server;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

final class PoseidonLegacyCompatGatewayBootstrap {
    private static volatile boolean installed;

    private PoseidonLegacyCompatGatewayBootstrap() {
    }

    static void ensureInstalled() {
        if (installed) {
            return;
        }

        synchronized (PoseidonLegacyCompatGatewayBootstrap.class) {
            if (installed) {
                return;
            }
            LegacyCompatGatewayRegistry.install(new PoseidonLegacyCompatGateway());
            installed = true;
        }
    }
}
