package net.minecraft.server;

import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

final class PoseidonNetworkCompatGatewayBootstrap {
    private static volatile boolean installed;

    private PoseidonNetworkCompatGatewayBootstrap() {
    }

    static void ensureInstalled() {
        if (installed) {
            return;
        }
        synchronized (PoseidonNetworkCompatGatewayBootstrap.class) {
            if (installed) {
                return;
            }
            NetworkCompatGatewayRegistry.install(new PoseidonNetworkCompatGateway());
            installed = true;
        }
    }
}
