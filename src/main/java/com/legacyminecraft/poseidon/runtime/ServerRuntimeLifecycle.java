package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.poseidon.PoseidonServer;
import com.legacyminecraft.poseidon.kernel.PoseidonKernel;

import java.util.logging.Level;

/**
 * Canonical runtime lifecycle hooks used by legacy server wrappers.
 */
public final class ServerRuntimeLifecycle {
    private static final ServerRuntimeLifecycle INSTANCE = new ServerRuntimeLifecycle();

    private ServerRuntimeLifecycle() {
    }

    public static ServerRuntimeLifecycle getInstance() {
        return INSTANCE;
    }

    public void onServerConstructed(MinecraftServer server) {
        PoseidonKernel.getInstance().registerService(MinecraftServer.class, server);
    }

    public void onServerInitialized() {
        PoseidonServer poseidonServer = Poseidon.getServer();
        if (poseidonServer != null) {
            try {
                poseidonServer.initializeServer();
            } catch (Throwable throwable) {
                MinecraftServer.log.log(Level.SEVERE, "[Poseidon] Failed to initialize modules", throwable);
            }
        }
    }

    public void onServerStopping() {
        PoseidonServer poseidonServer = Poseidon.getServer();
        if (poseidonServer != null) {
            try {
                poseidonServer.shutdownServer();
            } catch (Throwable throwable) {
                MinecraftServer.log.log(Level.SEVERE, "[Poseidon] Failed to shutdown modules cleanly", throwable);
            }
        }
    }
}
